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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.butor.netty.handler.codec.ftp.DataReceiver;
import com.butor.netty.handler.codec.ftp.impl.CwdCmd;
import com.butor.netty.handler.codec.ftp.impl.DeleCmd;
import com.butor.netty.handler.codec.ftp.impl.ListCmd;
import com.butor.netty.handler.codec.ftp.impl.MkdCmd;
import com.butor.netty.handler.codec.ftp.impl.NoopCmd;
import com.butor.netty.handler.codec.ftp.impl.PasvCmd;
import com.butor.netty.handler.codec.ftp.impl.PortCmd;
import com.butor.netty.handler.codec.ftp.impl.PwdCmd;
import com.butor.netty.handler.codec.ftp.impl.QuitCmd;
import com.butor.netty.handler.codec.ftp.impl.RmdCmd;
import com.butor.netty.handler.codec.ftp.impl.RnfrCmd;
import com.butor.netty.handler.codec.ftp.impl.RntoCmd;
import com.butor.netty.handler.codec.ftp.impl.StorCmd;
import com.butor.netty.handler.codec.ftp.impl.SystCmd;
import com.butor.netty.handler.codec.ftp.impl.TypeCmd;
import com.butor.netty.handler.codec.ftp.impl.UserCmd;

public class DefaultCommandExecutionTemplate extends CommandExecutionTemplate {
	
	private final  Map<String, FTPCommand> SUPPORTED_COMMAND_SET ;
	final ActivePassiveSocketManager activePassiveSocketManager;
	final DataReceiver dataReceiver;
	


	public DefaultCommandExecutionTemplate(DataReceiver dataReceiver) {
		this(new ActivePassiveSocketManager(new byte[] { 127, 0, 0, 1 }, 2121, 4242, 10),dataReceiver);
	}

	/*
	 *  
	 *  5.3.1.  FTP COMMANDS
	 *  The following are the FTP commands:

            USER <SP> <username> <CRLF>
            PASS <SP> <password> <CRLF>
            ACCT <SP> <account-information> <CRLF>
            CWD  <SP> <pathname> <CRLF>
            CDUP <CRLF>
            SMNT <SP> <pathname> <CRLF>
            QUIT <CRLF>
            REIN <CRLF>
            PORT <SP> <host-port> <CRLF>
            PASV <CRLF>
            TYPE <SP> <type-code> <CRLF>
            STRU <SP> <structure-code> <CRLF>
            MODE <SP> <mode-code> <CRLF>
            RETR <SP> <pathname> <CRLF>
            STOR <SP> <pathname> <CRLF>
            STOU <CRLF>
            APPE <SP> <pathname> <CRLF>
            ALLO <SP> <decimal-integer>
                [<SP> R <SP> <decimal-integer>] <CRLF>
            REST <SP> <marker> <CRLF>
            RNFR <SP> <pathname> <CRLF>
            RNTO <SP> <pathname> <CRLF>
            ABOR <CRLF>
            DELE <SP> <pathname> <CRLF>
            RMD  <SP> <pathname> <CRLF>
            MKD  <SP> <pathname> <CRLF>
            PWD  <CRLF>
            LIST [<SP> <pathname>] <CRLF>
            NLST [<SP> <pathname>] <CRLF>
            SITE <SP> <string> <CRLF>
            SYST <CRLF>
            STAT [<SP> <pathname>] <CRLF>
            HELP [<SP> <string>] <CRLF>
            NOOP <CRLF>
	 */
	
	
	

	@Override
	public FTPCommand getFTPCommand(String cmd) {
		return SUPPORTED_COMMAND_SET.get(cmd);
	}
	
	public DefaultCommandExecutionTemplate(
			final ActivePassiveSocketManager activePassiveSocketManager,
			final DataReceiver dataReceiver) {
		super();
		this.activePassiveSocketManager = activePassiveSocketManager;
		this.dataReceiver = dataReceiver;
		SUPPORTED_COMMAND_SET = Collections
				.unmodifiableMap(new HashMap<String, FTPCommand>() {
					Set<String> usedCmd = new HashSet<String>();
					private static final long serialVersionUID = 1L;
					{
						
						register(new CwdCmd());
						register(new DeleCmd());
						
						register(new ListCmd(activePassiveSocketManager));
						register(new MkdCmd());
						register(new NoopCmd());
						register(new PasvCmd(activePassiveSocketManager));
						register(new PortCmd(activePassiveSocketManager));
						register(new PwdCmd());
						register(new QuitCmd(activePassiveSocketManager));
						register(new RmdCmd());
						register(new RnfrCmd());
						register(new RntoCmd());
					
						register(new StorCmd(activePassiveSocketManager, dataReceiver));
						register(new SystCmd());
						register(new TypeCmd());
						register(new UserCmd());
					}

					void register(FTPCommand cmd) {
						String cmdStr =cmd.getCmd();
						if (usedCmd.contains(cmdStr)) {
							throw new IllegalArgumentException(String.format("Command already defined [%s] : %s",cmdStr,cmd.getClass().getName()));
						}
						put(cmdStr, cmd);

					}
				});
	}
	
}
