/**
 * Copyright (C) 2013 codingtony (t.bussieres@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.butor.netty.handler.codec.ftp.cmd;

import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommandExecutionTemplate {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	public abstract  FTPCommand  getFTPCommand(String cmd);
	
	
	public final void executeCommand(ChannelHandlerContext ctx, String cmd,String args) {
		//TODO handle properly FTP States see p.53 RFC 959
		FTPCommand command = getFTPCommand(cmd);
		if (command != null) {
			boolean loggedIn = ctx.channel().hasAttr(FTPAttrKeys.LOGGED_IN) ? ctx.channel().attr(FTPAttrKeys.LOGGED_IN).get() : false;
			boolean isLogonCommand = command instanceof LogonCommand;
			if ((!loggedIn && isLogonCommand) || (loggedIn && !isLogonCommand)) {
				command.execute(ctx, args);
				ctx.channel().attr(FTPAttrKeys.LAST_COMMAND).set(command);
				return;
			}
		}
		
		logger.debug("Command not supported {} {}",cmd,args);
		CommandUtil.send("500 Command not supported", ctx);
		
	}

}
