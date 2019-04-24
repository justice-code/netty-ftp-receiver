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
import com.butor.netty.handler.codec.ftp.cmd.FTPAttrKeys;

import io.netty.channel.ChannelHandlerContext;

public class CwdCmd extends AbstractFTPCommand {

	public CwdCmd() {
		super("CWD");
	}

	@Override
	public void execute(ChannelHandlerContext ctx, String args) {
		ctx.channel().attr(FTPAttrKeys.CWD).set(args);
		send("250 CWD command successful", ctx, args);
	}

}
