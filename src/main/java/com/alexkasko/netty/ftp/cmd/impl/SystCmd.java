package com.alexkasko.netty.ftp.cmd.impl;

import com.alexkasko.netty.ftp.cmd.AbstractFTPCommand;

import io.netty.channel.ChannelHandlerContext;

public class SystCmd extends AbstractFTPCommand {

	public SystCmd() {
		super("SYST");
	}

	@Override
	public void execute(ChannelHandlerContext ctx, String args) {
		send("215 UNIX Type: Java custom implementation", ctx,  args);
	}

}
