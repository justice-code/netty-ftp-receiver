package com.butor.netty.handler.codec.ftp;

import org.apache.commons.io.IOUtils;

import java.io.*;

public class FileDataReceiver implements DataReceiver{

    @Override
    public void receive(String name, InputStream data) throws IOException {
        String folder = FileDataReceiver.class.getClassLoader().getResource("download").getFile();
        try (OutputStream outputStream = new FileOutputStream(new File(folder, name))) {
            IOUtils.copy(data, outputStream);
        }
    }
}
