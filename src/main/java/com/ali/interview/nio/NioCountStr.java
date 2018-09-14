package com.ali.interview.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Created by IntelliJ IDEA.
 *
 * @author gangwen.xu
 * Date  : 2018/9/14
 * Time  : 下午4:57
 * 类描述 :
 */
public class NioCountStr {


    public static void main(String[] args) {
        try {
            File file = new File("src/main/resources/nio/bigFile.txt");
            FileInputStream fis = new FileInputStream(file);
            FileChannel fc = fis.getChannel();
            ByteBuffer bbuf = ByteBuffer.allocate(20);
            while (fc.read(bbuf) != -1) {
                bbuf.clear();
                String str = byteBufferToString(bbuf);

                //todo
                //会有字符串截断问题---
                //暂时想到的方案是文件重新拆小，拆的时候确定大小-
                System.out.print(str);
            }
            fc.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String byteBufferToString(ByteBuffer buffer) {
        CharBuffer charBuffer;
        try {
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer);
            buffer.flip();
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
