package com.ali.interview.filesort.flie;


import com.ali.interview.filesort.common.NamedThreadFactory;
import com.ali.interview.filesort.model.DataRecord;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author gangwen.xu
 * Date  : 2018/9/14
 * Time  : 下午14:53
 * 类描述 : 中间数据存储
 */
public class DataRepository {
    private static final int QUEUE_SIZE = 90;
    private static final int POOL_SIZE = 10;
    private LinkedBlockingQueue<DataRecord> blockingQueue;
    private SortedMap<String, DataRecord> sortedMap;
    private ExecutorService threadPool;

    public DataRepository() {
        threadPool = new ThreadPoolExecutor(POOL_SIZE, POOL_SIZE, 10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(QUEUE_SIZE), new NamedThreadFactory("file-read-task", false),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.printf("reject_ex");
                    }
                });

        blockingQueue = new LinkedBlockingQueue<>();
        sortedMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Long.valueOf(o1).compareTo(Long.valueOf(o2));
            }
        });
    }

    public LinkedBlockingQueue<DataRecord> getBlockingQueue() {
        return blockingQueue;
    }

    public void setBlockingQueue(LinkedBlockingQueue<DataRecord> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public SortedMap<String, DataRecord> getSortedMap() {
        return sortedMap;
    }

    public void setSortedMap(SortedMap<String, DataRecord> sortedMap) {
        this.sortedMap = sortedMap;
    }

    public ExecutorService getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }
}
