package com.cyt.jvm.ClassLoader;

import com.cyt.jvm.Test1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test4_1 {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        Test1 loader1 = new Test1("loader1");
        Test1 loader2 = new Test1("loader2");
        loader1.setPath("E:\\");
        loader2.setPath("E:\\");
        Class<?> class1 = loader1.loadClass("com.cyt.jvm.MyPerson");
        Class<?> class2 = loader2.loadClass("com.cyt.jvm.MyPerson");

        System.out.println(class1 == class2);

        Object obj1 = class1. newInstance();
        Object obj2 = class2. newInstance();

        Method method = class1.getMethod("setMyPerson",Object.class);
        method.invoke(obj1,obj2);
        // 两个没有父子关系的加载器，其所加载的类相互不可见
        /** 输出
         * findClass invoked : com.cyt.jvm.MyPerson
         * class loader name : loader1
         * findClass invoked : com.cyt.jvm.MyPerson
         * class loader name : loader2
         * false
         * Exception in thread "main" java.lang.reflect.InvocationTargetException
         * 	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
         * 	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
         * 	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
         * 	at java.lang.reflect.Method.invoke(Method.java:498)
         * 	at com.cyt.jvm.ClassLoader.Test4_1.main(Test4_1.java:23)
         * Caused by: java.lang.ClassCastException: com.cyt.jvm.MyPerson cannot be cast to com.cyt.jvm.MyPerson
         * 	at com.cyt.jvm.MyPerson.setMyPerson(MyPerson.java:7)
         * 	... 5 more
         */
    }
}
