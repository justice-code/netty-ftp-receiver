package com.alexkasko.netty.ftp.cmd.impl;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.alexkasko.netty.ftp.DataReceiver;
import com.alexkasko.netty.ftp.cmd.AbstractFTPCommand;
import com.alexkasko.netty.ftp.cmd.ActivePassiveSocketManager;
import com.alexkasko.netty.ftp.cmd.FTPAttrKeys;
import com.alexkasko.netty.ftp.cmd.FTPCommand;

public class StorCmd extends AbstractFTPCommand {

	
	private final ActivePassiveSocketManager activePassiveSocketManager;
	private final DataReceiver dataReceiver;
	
	public StorCmd(ActivePassiveSocketManager activePassiveSocketManager, DataReceiver dataReceiver) {
		super("STOR");
		this.activePassiveSocketManager = activePassiveSocketManager;
		this.dataReceiver = dataReceiver;
	}

	@Override
	public void execute(ChannelHandlerContext ctx, String args) {
		FTPCommand lastFTPCommand = ctx.attr(FTPAttrKeys.LAST_COMMAND).get();
		String lastCommand = lastFTPCommand != null ? lastFTPCommand.getCmd() : null;
		Socket activeSocket = activePassiveSocketManager.getActiveSocket(ctx);
		ServerSocket passiveSocket = activePassiveSocketManager.getPassiveSocket(ctx);
		
		if ("PORT".equals(lastCommand)) {
			if (null == activeSocket)
				send("503 Bad sequence of commands", ctx, args);
				send("150 Opening binary mode data connection for " + args, ctx,args);
			try {
				dataReceiver.receive(args, activeSocket.getInputStream());
				send("226 Transfer complete for STOR " + args, ctx, args);
			} catch (IOException e1) {
				logger.warn(
						"Exception thrown on reading through active socket: ["
								+ activeSocket + "]", e1);
				send("552 Requested file action aborted", ctx, args);
			} finally {
				activePassiveSocketManager.closeActiveSocket(ctx);
			}
		} else if ("PASV".equals(lastCommand)) {
			if (null == passiveSocket)
				send("503 Bad sequence of commands", ctx, args);
				send("150 Opening binary mode data connection for " + args, ctx, args);
			Socket clientSocket = null;
			try {
				clientSocket = passiveSocket.accept();
				dataReceiver.receive(args, clientSocket.getInputStream());
				send("226 Transfer complete for STOR " + args, ctx,  args);
			} catch (IOException e1) {
				logger.warn(
						"Exception thrown on reading through passive socket: ["
								+ passiveSocket + "], "
								+ "accepted client socket: [" + clientSocket
								+ "]", e1);
				send("552 Requested file action aborted", ctx, args);
			} finally {
				activePassiveSocketManager.closePassiveSocket(ctx);
			}
		} else
			send("503 Bad sequence of commands", ctx, args);
	}


}
