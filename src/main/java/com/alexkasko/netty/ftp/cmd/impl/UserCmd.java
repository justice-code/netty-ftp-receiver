package com.alexkasko.netty.ftp.cmd.impl;

import io.netty.channel.ChannelHandlerContext;

import com.alexkasko.netty.ftp.cmd.AbstractFTPCommand;
import com.alexkasko.netty.ftp.cmd.FTPAttrKeys;
import com.alexkasko.netty.ftp.cmd.LogonCommand;

public class UserCmd extends AbstractFTPCommand  implements LogonCommand {

	public UserCmd() {
		super("USER");
	}
	
	@Override
	public void execute(ChannelHandlerContext ctx, String args) {
		send("230 USER LOGGED IN", ctx, args);
		ctx.attr(FTPAttrKeys.LOGGED_IN).set(true);
	}
}
