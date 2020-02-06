package com.cyt.jvm;

public class MyTest3 {
    public MyTest3(){
        System.out.println("MyTest3 Loaded : " + this.getClass().getClassLoader());
//        new MyCat();
        new Test2();
    }
}
class MyCat{
    public MyCat() {
        System.out.println("MyCat Loaded : " + this.getClass().getClassLoader());
    }
}