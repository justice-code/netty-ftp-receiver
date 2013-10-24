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
package com.butor.netty.handler.codec.ftp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.butor.netty.handler.codec.ftp.cmd.CommandExecutionTemplate;

/**
 * Netty handler, partial implementation of <a
 * href="http://tools.ietf.org/html/rfc959">RFC 959
 * "File Transfer Protocol (FTP)"</a> for receiving FTP files. Both active and
 * passive modes are supported.
 * 
 * @author alexkasko Date: 12/27/12
 * 
 * 
 * 
 * Refactoring of the code to make it easier to implement new commands
 * 
 * Effort has been made to make the handler thread so that one instance of FtpServerHandler 
 * could be reused.
 * 
 * @author codingtony Date : 2013-10-17
 * 
 */
public class FtpServerHandler extends SimpleChannelInboundHandler<String> {

	private static final Logger logger = LoggerFactory
			.getLogger(FtpServerHandler.class);

	
	private static final Charset ASCII = CharsetUtil.US_ASCII;
	private final CommandExecutionTemplate excutionTemplate;


	/**
	 * Constructor for FTP active mode
	 * 
	 * @param receiver
	 *            data receiver implementation
	 */
	public FtpServerHandler(CommandExecutionTemplate executionTemplate) {
		this.excutionTemplate = executionTemplate;
	
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void channelRead0(ChannelHandlerContext ctx, String payload)
			throws Exception {
		String message = payload.trim();
		if (message.length() < 3)
			send("501 Syntax error", ctx);
		String cmd = 3 == message.length() ? message.substring(0, 3) : message
				.substring(0, 4).trim();
		String args = message.length() > cmd.length() ? message.substring(cmd
				.length() + 1) : "";
		
		excutionTemplate.executeCommand(ctx, cmd.toUpperCase(), args);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.error("Exception caught in FtpServerHandler", cause);
		send("500 Unspecified error", ctx);
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		send("220 Service ready", ctx);
	}


	private static void send(String response, ChannelHandlerContext ctx) {
		if (logger.isDebugEnabled()) {
			logger.debug("<- " + response);
		}
		String line = response + "\r\n";
		byte[] data = line.getBytes(ASCII);
		ctx.channel().writeAndFlush(Unpooled.wrappedBuffer(data));
	}


}
