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

public abstract class AbstractFTPCommand implements FTPCommand {

	
	protected static final byte CR = 13;
	protected static final byte LF = 10;
	protected static final byte[] CRLF = new byte[] { CR, LF };
	
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected final String cmd;
	
	public AbstractFTPCommand(String cmd) {
		// just make sure the commands are in upper case
		this.cmd = cmd.trim().toUpperCase();
	}
	
	public String getCmd() {
		return this.cmd;
	}
	
	protected void send(String response, ChannelHandlerContext ctx, String args) {
		if (logger.isDebugEnabled()) {
			logger.debug("-> {} {}",cmd,args);
			logger.debug("<- {}", response);
		}
		CommandUtil.send(response, ctx);
	}

	

	

}
