package com.alexkasko.netty.ftp.cmd.impl;

import com.alexkasko.netty.ftp.cmd.AbstractFTPCommand;

import io.netty.channel.ChannelHandlerContext;

public class MkdCmd extends AbstractFTPCommand {

	public MkdCmd() {
		super("MKD");
	}

	@Override
	public void execute(ChannelHandlerContext ctx, String args) {
		send("521 \"" + args + "\" directory exists", ctx, args);
	}

}
