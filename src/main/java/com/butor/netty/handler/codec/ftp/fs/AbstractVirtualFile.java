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
package com.butor.netty.handler.codec.ftp.fs;


public class AbstractVirtualFile {
	
	private final String name;
	private final Permission userPermission;
	private final Permission groupPermission;
	private final Permission otherPermission;
	
	public AbstractVirtualFile(String name, Permission userPermission, 
			 Permission groupPermission,
			 Permission otherPermission) {
		this.name = name;
		this.userPermission=userPermission;
		this.groupPermission=groupPermission;
		this.otherPermission=otherPermission;
	}
	
	public String getListString() {
		return String.format("%s%s%s%s 1 %d %d %10d %s %s\r\n",
				isDirectory()?"d":"-",userPermission.perm(),groupPermission.perm(),otherPermission.perm(),
						getUID(),getGID(),getSize(),getDate(),getName());
		
	}
	
	public int getUID() {
		return 1;
	}
	public int getGID() {
		return 1;
	}
	
	public boolean isDirectory() { return false; }
	public String getDate() { return "Jan 01 1970"; };
	
	public String getName() { return this.name; };
	public long getSize() { return 512; };

}
