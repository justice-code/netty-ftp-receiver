package com.alexkasko.netty.ftp.cmd.impl;

import com.alexkasko.netty.ftp.cmd.AbstractFTPCommand;

import io.netty.channel.ChannelHandlerContext;

public class RmdCmd extends AbstractFTPCommand {

	public RmdCmd() {
		super("RMD");
	}

	@Override
	public void execute(ChannelHandlerContext ctx, String args) {
		send("550 " + args + ": no such file or directory", ctx, args);
	}

}
