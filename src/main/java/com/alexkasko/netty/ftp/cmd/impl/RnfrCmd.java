package com.alexkasko.netty.ftp.cmd.impl;

import com.alexkasko.netty.ftp.cmd.AbstractFTPCommand;

import io.netty.channel.ChannelHandlerContext;

public class RnfrCmd extends AbstractFTPCommand {

	public RnfrCmd() {
		super("RNFR");
	}

	@Override
	public void execute(ChannelHandlerContext ctx, String args) {
		send("350 File exists, ready for destination name", ctx, args);
	}

}
