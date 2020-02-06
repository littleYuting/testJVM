package com.cyt.jvm.ClassLoader;

import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

public class Test7 {
    public static void main(String[] args) {
        /** 1. 若对上下文类加载器作如下设置，则输出会变成：
         * 当前线程上下文类加载器：sun.misc.Launcher$ExtClassLoader@14ae5a5
         * ServiceLoader 的类加载器null
         */
        Thread.currentThread().setContextClassLoader(Test7.class.getClassLoader().getParent());

        ServiceLoader<Driver> loader = ServiceLoader.load(Driver.class);
        Iterator<Driver> iterator = loader.iterator();

        while (iterator.hasNext()) {
            Driver driver = iterator.next();
            System.out.println("driver:" + driver.getClass() + ",  loader:" + driver.getClass().getClassLoader());
        }
        System.out.println("当前线程上下文类加载器：" + Thread.currentThread().getContextClassLoader());
        System.out.println("ServiceLoader 的类加载器" + ServiceLoader.class.getClassLoader());
        /** 输出
         * driver:class com.mysql.jdbc.Driver,  loader:sun.misc.Launcher$AppClassLoader@18b4aac2
         * 当前线程上下文类加载器：sun.misc.Launcher$AppClassLoader@18b4aac2
         * ServiceLoader 的类加载器null
         */
    }
}
