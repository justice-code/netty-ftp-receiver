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

public enum FTPResponse {
	RESTART_MARKER_REPLY(110,"Restart marker reply."),
	FILE_STATUS_OKAY(150,"File status okay; about to open data connection."),
	SYNTAX_ERROR(501,"Syntax error");
	
	final private int code;
	final private String text;
	
	FTPResponse(int code,String text) {
		this.code  = code;
		this.text = text;
	}
	
	protected int getCode() {
		return code;
	}

	protected String getText() {
		return text;
	}

}
