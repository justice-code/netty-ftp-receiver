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

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * {@link FrameDecoder} implementation, that accumulates input strings until {@code \r\n}
 * and sends accumulated string upstream.
 *
 * @author alexkasko
 * Date: 12/28/12
 */
public class CrlfStringDecoder extends ByteToMessageDecoder {
    private static final byte CR = 13;
    private static final byte LF = 10;

    private final int maxRequestLengthBytes;
    private final Charset encoding;

    /**
     * Constructor, uses {@code 256} max string length and {@code UTF-8} encoding
     */
    public CrlfStringDecoder() {
        this(1<< 8, "UTF-8");
    }

    /**
     * Constructor
     *
     * @param maxRequestLengthBytes max length of accumulated string in bytes
     * @param encoding string encoding to use before sending it upstream
     */
    public CrlfStringDecoder(int maxRequestLengthBytes, String encoding) {
        if(maxRequestLengthBytes <= 0) throw new IllegalArgumentException(
                "Provided maxRequestLengthBytes: [" + maxRequestLengthBytes +"] must be positive");
        this.maxRequestLengthBytes = maxRequestLengthBytes;
        this.encoding = Charset.forName(encoding);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf cb, List<Object> out) throws Exception {
        byte[] data = new byte[maxRequestLengthBytes];
        int lineLength = 0;
        while (cb.isReadable()) {
            byte nextByte = cb.readByte();
            if (nextByte == CR) {
                nextByte = cb.readByte();
                if (nextByte == LF) {
                	out.add(new String(data, encoding));
                }
            } else if (nextByte == LF) {
            	out.add(new String(data, encoding));
            } else {
                if (lineLength >= maxRequestLengthBytes) throw new IllegalArgumentException(
                        "Request size threshold exceeded: [" + maxRequestLengthBytes + "]");
                data[lineLength] = nextByte;
                lineLength += 1;
            }
        }
	}
}
