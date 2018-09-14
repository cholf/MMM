package com.ali.interview.filesort.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by IntelliJ IDEA.
 *
 * @author gangwen.xu
 * Date  : 2018/9/14
 * Time  : 下午2:50
 * 类描述 : 带名字的线程工厂
 */
public class NamedThreadFactory implements ThreadFactory {

    /**
     * 线程号维护
     */
    private final AtomicInteger threadNum = new AtomicInteger(1);

    /**
     * 线程名称前缀
     */
    private final String prefix;

    /**
     * 是否守护进程
     */
    private final boolean daemon;

    public NamedThreadFactory(String prefix) {
        this(prefix, false);
    }

    public NamedThreadFactory(String inPrefix, boolean inDaemon) {
        prefix = inPrefix + "-thread-";
        daemon = inDaemon;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        String name = prefix + threadNum.getAndIncrement();
        Thread ret = new Thread(runnable, name);
        ret.setDaemon(daemon);
        return ret;
    }

}
