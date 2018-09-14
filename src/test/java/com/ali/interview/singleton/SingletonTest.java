package com.ali.interview.singleton;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author gangwen.xu
 * Date  : 2018/9/14
 * Time  : 下午4:11
 * 类描述 :
 */
public class SingletonTest {

    @Test
    public void MultTaskTest() {
        // 构造一个Runner
        TestRunnable runner = new TestRunnable() {
            @Override
            public void runTest() throws Throwable {
                //todo  断言
                //打印hashcode
                System.out.println("__" + Singleton.getInstance().hashCode());
            }
        };

        //想当于并发多少个。
        int runnerCount = 30;
        TestRunnable[] trs = new TestRunnable[runnerCount];
        for (int i = 0; i < runnerCount; i++) {
            trs[i] = runner;
        }

        // 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入
        MultiThreadedTestRunner multiThreadedTestRunner = new MultiThreadedTestRunner(trs);
        try {
            // 开发并发执行数组里定义的内容
            multiThreadedTestRunner.runTestRunnables();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}