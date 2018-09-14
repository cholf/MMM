package com.ali.interview.filesort.flie;

import com.ali.interview.filesort.model.DataRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by IntelliJ IDEA.
 *
 * @author gangwen.xu
 * Date  : 2018/9/14
 * Time  : 下午14:28
 * 类描述 :
 */
public class ReadTask implements Runnable {
    private LinkedBlockingQueue<DataRecord> queue;
    private File file;
    private CountDownLatch countDownLatch;

    public ReadTask(File file, LinkedBlockingQueue<DataRecord> queue, CountDownLatch latch) {
        this.file = file;
        this.queue = queue;
        this.countDownLatch = latch;
    }

    @Override
    public void run() {
        try {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = new BufferedReader(read);
            String line;
            String[] arrs;
            while ((line = br.readLine()) != null) {
                if ("".equals(line)) {
                    continue;
                }
                //todo
                arrs = line.split(",");
                DataRecord dataRecord = new DataRecord();
                dataRecord.setId(arrs[0]);
                dataRecord.setGroupId(arrs[1]);
                dataRecord.setQuota(new Float(arrs[2]));
                queue.add(dataRecord);
            }
            br.close();
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }
}
