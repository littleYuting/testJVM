package com.cyt.jvm;

public class Test2 {
    public Test2(){
        System.out.println("Test2 Loaded : " + this.getClass().getClassLoader());
    }
//    public static void main(String[] args) {
//        System.out.println(MyChild11.str2);
//    }
}
class MyParent11{
    public static String str = "cyt";
    static {
        System.out.println("MyParent static block");
    }
}
class MyChild11 extends MyParent1{
    public static String str2 = "peace";
    static {
        System.out.println("MyChild static block");
    }
}
