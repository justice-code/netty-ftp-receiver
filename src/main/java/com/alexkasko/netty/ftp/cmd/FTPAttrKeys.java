package com.alexkasko.netty.ftp.cmd;

import java.net.ServerSocket;
import java.net.Socket;

import io.netty.util.AttributeKey;

public class FTPAttrKeys  {
	
	
	public static final AttributeKey<String> CWD = new AttributeKey<String>("CWD");
	
	public static final AttributeKey<Boolean> LOGGED_IN = new AttributeKey<Boolean>("LOGGED_IN");
	public static final AttributeKey<Socket> ACTIVE_SOCKET = new AttributeKey<Socket>("ACTIVE_SOCKET");
	public static final AttributeKey<ServerSocket> PASSIVE_SOCKET = new AttributeKey<ServerSocket>("PASSIVE_SOCKET");
	
	public static final AttributeKey<FTPCommand> LAST_COMMAND = new AttributeKey<FTPCommand>("LAST_FTP_COMMAND");
	
}
