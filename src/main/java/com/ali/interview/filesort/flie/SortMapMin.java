package com.ali.interview.filesort.flie;

import com.ali.interview.filesort.model.DataRecord;

import java.util.SortedMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by IntelliJ IDEA.
 *
 * @author gangwen.xu
 * Date  : 2018/9/14
 * Time  : 下午3:48
 * 类描述 :
 */
public class SortMapMin implements Runnable {
    private LinkedBlockingQueue<DataRecord> queue;
    private SortedMap<String, DataRecord> sortedMap;
    private CountDownLatch countDownLatch;

    public SortMapMin(LinkedBlockingQueue<DataRecord> queue, SortedMap<String, DataRecord> sortedMap, CountDownLatch countDownLatch) {
        this.queue = queue;
        this.sortedMap = sortedMap;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            for (; ; ) {
                if (!queue.isEmpty()) {
                    DataRecord dataRecord = queue.take();
                    DataRecord tempRecord = sortedMap.get(dataRecord.getGroupId());
                    //排序
                    if (tempRecord == null) {
                        sortedMap.put(dataRecord.getGroupId(), dataRecord);
                    } else {
                        if (dataRecord.getQuota() < tempRecord.getQuota()) {
                            sortedMap.put(dataRecord.getGroupId(), dataRecord);
                        }
                    }
                } else {
                    if (countDownLatch.getCount() <= 1) {
                        countDownLatch.countDown();
                        break;
                    }
                }
            }
        }catch (InterruptedException in){
            System.out.print(in.getMessage());
        }
    }
}