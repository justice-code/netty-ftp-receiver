package com.alexkasko.netty.ftp.cmd.impl;

import com.alexkasko.netty.ftp.cmd.AbstractFTPCommand;
import com.alexkasko.netty.ftp.cmd.FTPAttrKeys;

import io.netty.channel.ChannelHandlerContext;

public class CwdCmd extends AbstractFTPCommand {

	public CwdCmd() {
		super("CWD");
	}

	@Override
	public void execute(ChannelHandlerContext ctx, String args) {
		ctx.attr(FTPAttrKeys.CWD).set(args);
		send("250 CWD command successful", ctx, args);
	}

}
