package com.lilbpg.pgquery;

import java.io.*;

public class PgQuery {

    static {
        try {
            String lib = "/libpgquery_jni.so";

            // 从 JAR 内部读取 .so
            InputStream in = PgQuery.class.getResourceAsStream(lib);
            if (in == null) {
                throw new RuntimeException("Cannot load JNI library from jar: " + lib);
            }

            // 创建临时文件
            File temp = File.createTempFile("libpgquery_jni", ".so");
            temp.deleteOnExit();

            try (FileOutputStream out = new FileOutputStream(temp)) {
                byte[] buf = new byte[4096];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }

            // 显式加载
            System.load(temp.getAbsolutePath());

        } catch (Exception e) {
            throw new RuntimeException("Failed to load JNI library", e);
        }
    }

    // 声明 native 方法
    public static native String parse(String sql);

}
