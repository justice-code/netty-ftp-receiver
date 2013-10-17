package com.alexkasko.netty.ftp.cmd;

import io.netty.channel.ChannelHandlerContext;

public interface FTPCommand {
	
	String getCmd();
	void execute(ChannelHandlerContext ctx, String args);
	
}
