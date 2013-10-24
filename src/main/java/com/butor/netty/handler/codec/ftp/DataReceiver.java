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

import java.io.IOException;
import java.io.InputStream;

/**
 * Implementation should read all required data from provided FTP file-upload stream,
 * stream will be closed immediately after {@link #receive(String, java.io.InputStream)} call
 *
 * @author alexkasko
 * Date: 12/28/12
 */
public interface DataReceiver {
    /**
     * Implementation should read provided FTP file-upload data
     *
     * @param name name of uploaded file
     * @param data uploaded file stream
     * @throws IOException on IO error
     */
    void receive(String name, InputStream data) throws IOException;
}
