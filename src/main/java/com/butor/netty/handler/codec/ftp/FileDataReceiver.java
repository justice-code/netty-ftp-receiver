package com.butor.netty.handler.codec.ftp;

import org.apache.commons.io.IOUtils;

import java.io.*;

public class FileDataReceiver implements DataReceiver{

    @Override
    public void receive(String name, InputStream data) throws IOException {
        String folder = FileDataReceiver.class.getClassLoader().getResource("download").getFile();
        File file = new File(folder, name);
//        if (file.exists()) {
//            return;
//        }

        try (OutputStream outputStream = new FileOutputStream(file)) {
            IOUtils.copy(data, outputStream);
        }
    }
}
