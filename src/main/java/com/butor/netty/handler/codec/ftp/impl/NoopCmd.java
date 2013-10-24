package com.alexkasko.netty.ftp.cmd.impl;

import com.alexkasko.netty.ftp.cmd.AbstractFTPCommand;

import io.netty.channel.ChannelHandlerContext;

public class NoopCmd extends AbstractFTPCommand {

	public NoopCmd() {
		super("NOOP");
	}

	@Override
	public void execute(ChannelHandlerContext ctx, String args) {
		send("200 OK", ctx, args);
	}

}
