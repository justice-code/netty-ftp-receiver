package com.alexkasko.netty.ftp.cmd.impl;

import com.alexkasko.netty.ftp.cmd.AbstractFTPCommand;
import com.alexkasko.netty.ftp.cmd.FTPAttrKeys;

import io.netty.channel.ChannelHandlerContext;

public class PwdCmd extends AbstractFTPCommand {

	
	public PwdCmd() {
		super("PWD");
	}

	@Override
	public void execute(ChannelHandlerContext ctx, String args) {
		String curDir = ctx.attr(FTPAttrKeys.CWD).get();
		send(String.format("257 \"%s\" is current directory",curDir == null ? "/" : curDir),ctx,args);
	}

}
