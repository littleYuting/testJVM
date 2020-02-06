package com.cyt.jvm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test4 {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        Test1 loader1 = new Test1("loader1");
        Test1 loader2 = new Test1("loader2");

        Class<?> class1 = loader1.loadClass("com.cyt.jvm.MyPerson");
        Class<?> class2 = loader2.loadClass("com.cyt.jvm.MyPerson");

        System.out.println(class1 == class2);//true

        Object obj1 = class1. newInstance();
        Object obj2 = class2. newInstance();

        Method method = class1.getMethod("setMyPerson",Object.class);
        method.invoke(obj1,obj2);//不报错，在同一个命名空间内相互可见
    }
}
