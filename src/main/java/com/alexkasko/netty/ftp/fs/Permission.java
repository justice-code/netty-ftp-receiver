package com.alexkasko.netty.ftp.fs;

public enum Permission {
	NONE("---"),
	READ("r--"),
	WRITE("-w-"),
	EXEC("--x"),
	READWRITE("rw-"),
	READEXEC("r-x"),
	WRITEEXEC("-wx"),
	READWRITEEXEC("rwx");
	
	private final String permString;
	
	Permission(String permString) {
		this.permString = permString;
	}
	
	protected String perm() {
		return permString;
	}
}