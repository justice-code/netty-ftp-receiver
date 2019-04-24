package com.alexkasko.netty.ftp;

import com.butor.netty.handler.codec.ftp.CrlfStringDecoder;
import com.butor.netty.handler.codec.ftp.FileDataReceiver;
import com.butor.netty.handler.codec.ftp.FtpServerHandler;
import com.butor.netty.handler.codec.ftp.cmd.DefaultCommandExecutionTemplate;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DownloadTest {

    @Test
    public void test() throws IOException {
        final DefaultCommandExecutionTemplate defaultCommandExecutionTemplate = new DefaultCommandExecutionTemplate(new FileDataReceiver());
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipe = ch.pipeline();
                        pipe.addLast("decoder", new CrlfStringDecoder());
                        pipe.addLast("handler", new FtpServerHandler(defaultCommandExecutionTemplate));
                    }

                });
        b.localAddress(2121).bind();
        FTPClient client = new FTPClient();
//        https://issues.apache.org/jira/browse/NET-493

        client.setBufferSize(0);
        client.connect("127.0.0.1", 2121);
        assertEquals(230,client.user("anonymous"));
        assertTrue(client.setFileType(FTP.BINARY_FILE_TYPE));

        client.retrieveFile("libpcap-1.7.4.tar.gz", new FileOutputStream(new File("./libpcap-1.7.4.tar.gz")));

    }
}
