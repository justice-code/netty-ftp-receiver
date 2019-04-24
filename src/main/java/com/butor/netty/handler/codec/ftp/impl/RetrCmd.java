package com.butor.netty.handler.codec.ftp.impl;

import com.butor.netty.handler.codec.ftp.cmd.AbstractFTPCommand;
import com.butor.netty.handler.codec.ftp.cmd.ActivePassiveSocketManager;
import com.butor.netty.handler.codec.ftp.cmd.FTPAttrKeys;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;

public class RetrCmd extends AbstractFTPCommand {
    private static final String separator = "#";

    private final ActivePassiveSocketManager activePassiveSocketManager;

    public RetrCmd(ActivePassiveSocketManager activePassiveSocketManager) {
        super("RETR");
        this.activePassiveSocketManager = activePassiveSocketManager;
    }

    @Override
    public void execute(ChannelHandlerContext ctx, String args) {
        String name = args;
        int start = 0;
        if (args.contains(separator)) {
            String[] arr = args.split(separator);
            name = arr[0];
            start = Integer.parseInt(arr[1]);
        }

        URL url = RetrCmd.class.getClassLoader().getResource("download/" + name);
        if (null == url) {
            send("550 File not found. " + args, ctx, args);
            return;
        }

        byte[] content;
        try (InputStream inputStream = url.openStream()) {
            content = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            logger.error("Retr error", e);
            send("550 File not found. " + args, ctx, args);
            return;
        }
        content = Arrays.copyOfRange(content, start, content.length);

        String lastCommand = ctx.channel().attr(FTPAttrKeys.LAST_COMMAND).get() != null ? ctx.channel().attr(FTPAttrKeys.LAST_COMMAND).get().getCmd() : null;
        Socket socket;
        if ("PORT".equals(lastCommand)) {
            socket = activePassiveSocketManager.getActiveSocket(ctx);
        } else if ("PASV".equals(lastCommand)){
            try {
                socket = activePassiveSocketManager.getPassiveSocket(ctx).accept();
            } catch (IOException e) {
                logger.error("Retr error", e);
                send("503 Bad sequence of commands", ctx, args);
                return;
            }
        } else {
            socket = null;
        }

        if (null == socket) {
            send("503 Bad sequence of commands", ctx, args);
            return;
        }

        try {

            send("150 Opening data connection" + args, ctx, args);
            socket.getOutputStream().write(content);
            send("226 Transfer complete for RETR", ctx, args);
        } catch (IOException e) {
            logger.error("Retr error", e);
            send("503 Bad sequence of commands", ctx, args);
        } finally {
            if ("PORT".equals(lastCommand)) {
                activePassiveSocketManager.closeActiveSocket(ctx);
            } else if ("PASV".equals(lastCommand)) {
                activePassiveSocketManager.closePassiveSocket(ctx);
            }
        }

    }

}
