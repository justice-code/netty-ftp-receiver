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

import java.net.ServerSocket;
import java.net.Socket;

import io.netty.util.AttributeKey;

public class FTPAttrKeys  {
	
	
	public static final AttributeKey<String> CWD = AttributeKey.valueOf("CWD");
	
	public static final AttributeKey<Boolean> LOGGED_IN = AttributeKey.valueOf("LOGGED_IN");
	public static final AttributeKey<Socket> ACTIVE_SOCKET = AttributeKey.valueOf("ACTIVE_SOCKET");
	public static final AttributeKey<ServerSocket> PASSIVE_SOCKET = AttributeKey.valueOf("PASSIVE_SOCKET");
	
	public static final AttributeKey<FTPCommand> LAST_COMMAND = AttributeKey.valueOf("LAST_FTP_COMMAND");
	
}
