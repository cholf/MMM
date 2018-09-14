package com.ali.interview.singleton;

/**
 * Created by IntelliJ IDEA.
 *
 * @author gangwen.xu
 * Date  : 2018/9/14
 * Time  : 下午12:57
 * 类描述 :
 */
public class Singleton {

    private static class InstanceManager {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return InstanceManager.INSTANCE;
    }
}
