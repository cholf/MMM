package com.ali.interview.filesort.flie;

import com.ali.interview.filesort.FileSortService;

/**
 * Created by IntelliJ IDEA.
 *
 * @author gangwen.xu
 * Date  : 2018/9/14
 * Time  : 下午4:01
 * 类描述 :
 */

public class FileSortTaskTest {
    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        FileSortService sortService = new FileSortService();
        String sourcePath = "src/main/resources/sources";
        sortService.sortFile(sourcePath);
    }
}