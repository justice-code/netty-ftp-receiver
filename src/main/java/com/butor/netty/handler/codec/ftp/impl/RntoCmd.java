package com.alexkasko.netty.ftp.cmd.impl;

import com.alexkasko.netty.ftp.cmd.AbstractFTPCommand;

import io.netty.channel.ChannelHandlerContext;

public class RntoCmd extends AbstractFTPCommand {

	public RntoCmd() {
		super("RNTO");
	}

	@Override
	public void execute(ChannelHandlerContext ctx, String args) {
		send("250 RNTO command successful", ctx, args);
	}

}
