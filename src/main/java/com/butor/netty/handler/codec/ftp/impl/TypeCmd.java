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
package com.butor.netty.handler.codec.ftp.impl;

import com.butor.netty.handler.codec.ftp.cmd.AbstractFTPCommand;

import io.netty.channel.ChannelHandlerContext;

public class TypeCmd extends AbstractFTPCommand {

	public TypeCmd() {
		super("TYPE");
	}

	@Override
	public void execute(ChannelHandlerContext ctx, String args) {
		if ("I".equals(args))
			send("200 Type set to IMAGE NONPRINT", ctx,args);
		else if ("A".equals(args))
			send("200 Type set to ASCII NONPRINT", ctx, args);
		else
			send("504 Command not implemented for that parameter", ctx, args);

	}

}
