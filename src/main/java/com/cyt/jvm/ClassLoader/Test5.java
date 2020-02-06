package com.cyt.jvm.ClassLoader;
import com.cyt.jvm.Test4;

public class Test5 {
    static {
        System.out.println("MyTest5 initializer");
    }

    public static void main(String[] args) {
        System.out.println(Test5.class.getClassLoader());
        System.out.println(Test4.class.getClassLoader());
    }
}
