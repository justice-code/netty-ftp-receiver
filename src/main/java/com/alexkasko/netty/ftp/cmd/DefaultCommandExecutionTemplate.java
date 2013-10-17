package com.alexkasko.netty.ftp.cmd;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alexkasko.netty.ftp.DataReceiver;
import com.alexkasko.netty.ftp.cmd.impl.CwdCmd;
import com.alexkasko.netty.ftp.cmd.impl.DeleCmd;
import com.alexkasko.netty.ftp.cmd.impl.ListCmd;
import com.alexkasko.netty.ftp.cmd.impl.MkdCmd;
import com.alexkasko.netty.ftp.cmd.impl.NoopCmd;
import com.alexkasko.netty.ftp.cmd.impl.PasvCmd;
import com.alexkasko.netty.ftp.cmd.impl.PortCmd;
import com.alexkasko.netty.ftp.cmd.impl.PwdCmd;
import com.alexkasko.netty.ftp.cmd.impl.QuitCmd;
import com.alexkasko.netty.ftp.cmd.impl.RmdCmd;
import com.alexkasko.netty.ftp.cmd.impl.RnfrCmd;
import com.alexkasko.netty.ftp.cmd.impl.RntoCmd;
import com.alexkasko.netty.ftp.cmd.impl.StorCmd;
import com.alexkasko.netty.ftp.cmd.impl.SystCmd;
import com.alexkasko.netty.ftp.cmd.impl.TypeCmd;
import com.alexkasko.netty.ftp.cmd.impl.UserCmd;

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
