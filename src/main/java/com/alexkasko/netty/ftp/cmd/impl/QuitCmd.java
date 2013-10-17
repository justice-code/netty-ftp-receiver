package com.alexkasko.netty.ftp.cmd.impl;

import io.netty.channel.ChannelHandlerContext;

import com.alexkasko.netty.ftp.cmd.AbstractFTPCommand;
import com.alexkasko.netty.ftp.cmd.ActivePassiveSocketManager;

public class QuitCmd extends AbstractFTPCommand {

	private final ActivePassiveSocketManager activePassiveSocketManager;
	
	public QuitCmd(ActivePassiveSocketManager activePassiveSocketManager) {
		super("QUIT");
		this.activePassiveSocketManager = activePassiveSocketManager;
	}

	@Override
	public void execute(ChannelHandlerContext ctx, String args) {
		send("221 Service closing control connection.", ctx, args);
		activePassiveSocketManager.closeActiveSocket(ctx);
		activePassiveSocketManager.closePassiveSocket(ctx);
		ctx.close();
	}

}
