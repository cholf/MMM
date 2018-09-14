package com.ali.interview.filesort;

import com.ali.interview.filesort.flie.DataRepository;
import com.ali.interview.filesort.flie.ReadTask;
import com.ali.interview.filesort.flie.SortMapMin;
import com.ali.interview.filesort.model.DataRecord;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by IntelliJ IDEA.
 *
 * @author gangwen.xu
 * Date  : 2018/9/14
 * Time  : 下午4:58
 * 类描述 :
 */
public class FileSortService {

    /**
     * 文件排序
     *
     * @param sourcePath str
     */
    public void sortFile(String sourcePath) {

//1.读取文件夹文件
        File sourceFile = new File(sourcePath);
        File[] files = sourceFile.listFiles();
        if (files == null || files.length < 1) {
            System.out.println("file_null_or_size_equal_zero");
            return;
        }

        //2.单文件线程池处理
        DataRepository repository = new DataRepository();
        CountDownLatch countDownLatch = new CountDownLatch(files.length + 1);
        for (File file : files) {
            ReadTask readTask = new ReadTask(file, repository.getBlockingQueue(), countDownLatch);
            repository.getThreadPool().execute(readTask);
        }

        //3.group_id-放入map
        SortMapMin sortMapMin = new SortMapMin(repository.getBlockingQueue(), repository.getSortedMap(), countDownLatch);
        Thread sortThread = new Thread(sortMapMin);
        sortThread.start();
        try {
            countDownLatch.await();
            repository.getThreadPool().shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //4.输出
        Iterator<Map.Entry<String, DataRecord>> it = repository.getSortedMap().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, DataRecord> entry = it.next();
            DataRecord dataRecord = entry.getValue();
            System.out.println(dataRecord.getGroupId() + "，" + dataRecord.getId() + "，" + dataRecord.getQuota());
        }
    }

}
