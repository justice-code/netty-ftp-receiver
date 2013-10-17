package com.alexkasko.netty.ftp;

import com.alexkasko.netty.ftp.cmd.DefaultCommandExecutionTemplate;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class StartServer {
	
	public static void main(String... args) throws Exception {
    	final DefaultCommandExecutionTemplate defaultCommandExecutionTemplate = new DefaultCommandExecutionTemplate(new ConsoleReceiver());

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
    	b.localAddress(2121).bind().channel().closeFuture().sync();
	}

}
