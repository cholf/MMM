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
            String testStr = "hello";
            String cacheStr = "";
            String blankStr = " ";
            FileInputStream fis = new FileInputStream(file);
            FileChannel fc = fis.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(9);
            int count = 0;
            while (fc.read(byteBuffer) != -1) {
                byteBuffer.clear();
                String str = byteBufferToString(byteBuffer);
                if (str != null && str.length() > 0) {
                    //非空格开始--校验是否有分割发生
                    if (!str.startsWith(blankStr) && cacheStr.length() > 0) {
                        String headStr = str.split(blankStr)[0].replace("\r\n", "").trim();
                        if (testStr.equals(cacheStr + headStr)) {
                            count++;
                        }
                    }
                    //非空格结束，获取上一个空格后的字符
                    if (!str.endsWith(blankStr)) {
                        int start = str.lastIndexOf(blankStr);
                        if (start != -1) {
                            String lastStr = str.substring(start + 1, str.length());
                            if (testStr.contains(lastStr.trim())) {
                                //字符有包含关系-排除相等字符
                                if (testStr.equals(lastStr)) {
                                    count++;
                                } else {
                                    //缓存字符可能的左部分
                                    cacheStr = lastStr;
                                }
                            }
                        }
                    }
                    //普通字符计数
                    String[] baseSplit = str.split(blankStr);
                    for (String baseStr : baseSplit) {
                        String arrayStr = baseStr.replace("\r\n", "");
                        if (testStr.equals(arrayStr)) {
                            count++;
                        }
                    }
                }
            }
            System.out.printf(testStr + "出现次数：" + count);
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
