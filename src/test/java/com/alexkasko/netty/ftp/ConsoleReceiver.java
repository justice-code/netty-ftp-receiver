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
package com.alexkasko.netty.ftp;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.butor.netty.handler.codec.ftp.DataReceiver;

class ConsoleReceiver implements DataReceiver {
    @Override
    public void receive(String name, InputStream data) throws IOException {
        System.out.println("receiving file: [" + name + "]");
        System.out.println("receiving data:");
        IOUtils.copy(data, System.out);
        System.out.println("");
    }
}