package com.cyt.jvm;

import java.io.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.Random;
import java.util.UUID;

public class testClassLoader {
    static {
        System.out.println("ClassLoader main static block");
    }
    public static void main(String[] args) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {

//        System.out.println(MyChild1.str);
//        /** 对于静态字段来说，只有定义了该字段的类才会被初始化
//         * MyParent static block
//         * cyt
//         */
//        System.out.println(MyChild1.str2);
//        /** 当一个类在初始化时，要求其父类全部都已初始化完毕
//         * MyParent static block
//         * MyChild static block
//         * peace
//         */
//        System.out.println(MyParent2.str);
//        System.out.println(MyParent3.str); // output: MyParent3 static block ; 6cb797a5-00a4-466e-b755-282c33ad8b2d
//        /**
//         *  常量在编译阶段会存入到调用这个常量方法所在的类常量池中，但是對於在編譯期無法確定的常量，則會調用所在類的初始化
//         *  本质上，调用类并没有直接引用到定义常量的类，因此并不会触发定义常量的类的初始化
//         *  助记符： ldc 表示将 int、float 或 String 类型的常量从常量池中推送至栈顶
//         *          bipush 表示将单字节（-128~127）的常量值推送至栈顶；
//         *          sipush 表示将一个短整型常量值（-32768~32767）推送至栈顶
//         *          iconst_1 表示将 int 类型 1 推送至栈顶（iconst_m1 ~ iconst_5）
//         *          anewarray 表示创建一个引用类型的（eg 类、接口）数组，并将其引用值压入栈顶
//         *          newarray 表示创建一个原始类型（eg int、float）的数组，并将其引用值压入栈顶
//         */
//        MyParent4 myParent4 = new MyParent4(); // output: MyParent4 static block
//        MyParent4 myParent41 = new MyParent4();
//        MyParent4[] myParent4s = new MyParent4[1]; // 并不会初始化，对于数组实例来说，其类型是由 JVM 在运行期动态生成的，其父类型是 Object

        // 当一个接口在初始化时，并不要求其父接口都完成初始化
        // 只有在真正使用到父接口的时候（如引用接口中所定义的常量时），才会初始化
//        System.out.println(MyChild_interface_5.b); // output:6 【父接口会被加载，但不会初始化】
//        System.out.println(MyChild_class_5.b); // output:MyParent_class_5 invoke; 6【父类会被初始化】
//        System.out.println(MyChild_class_5.c); // 会直接放入调用类的常量池中，而不会加载子类、父类，更不会初始化父类
//        testSingleton();
//        testClassLoader();
//        test6();
        /**
         * ClassLoader main static block
         * ------------------------
         * MyParent6 static block
         * ------------------------
         * 2
         * ------------------------
         * MyChild6 static block
         * 3
         */
//        getResource();
//        testArrayClassLoader();
//        testSelfDefineClassLoader();

    }

    public static void testSingleton(){
        Singleton singleton = Singleton.getInstance();
        System.out.println("counter1: " + singleton.counter1);
        System.out.println("counter2: " + singleton.counter2);
    }

    public static void testClassLoader() throws ClassNotFoundException {
        Class<?> class1 = Class.forName("java.lang.String");
        System.out.println(class1.getClassLoader());// null ，根类加载器

        Class<?> class2 = Class.forName("com.cyt.jvm.C");
        System.out.println(class2.getClassLoader());//class C static block; sun.misc.Launcher$AppClassLoader@18b4aac2，系统类加载器

        // 调用ClassLoader的loadClass方法加载一个类，并不是对类的主动使用，不会导致类的初始化
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        Class<?> class3 = loader.loadClass("com.cyt.jvm.C");
        System.out.println(class3.getClassLoader());//sun.misc.Launcher$AppClassLoader@18b4aac2
    }
    public static void test6(){
        MyParent6 myParent6;
        System.out.println("------------------------");
        myParent6 = new MyParent6();
        System.out.println("------------------------");
        System.out.println(myParent6.a);
//        MyParent6.doSomething();
        System.out.println("------------------------");
//        System.out.println(MyChild6.a); //通过子类调用父类，只会造成父类的主动使用
        System.out.println(MyChild6.b);
    }

    public static void getResource() throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String resourceName = "com/cyt/jvm/C.class";
        Enumeration<URL> urls = loader.getResources(resourceName);
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            System.out.println(url);
        }
        //output: file:/D:/IDEA/JavaCode/testJVM/target/classes/com/cyt/jvm/C.class 【磁盘路径】
    }

    public static void testArrayClassLoader(){
        // 数组的类加载器在 jvm 运行期间由其子元素的类加载器动态确定
        String[] strings = new String[2];
        System.out.println(strings.getClass().getClassLoader());//null 根类加载器
        System.out.println("-----------------------");
        testClassLoader[] testClassLoaders = new testClassLoader[2];
        System.out.println(testClassLoaders.getClass().getClassLoader());// sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println("-----------------------");
        int[] ints = new int[2];
        System.out.println(ints.getClass().getClassLoader());//null 原生类型不存在类加载器
    }

    public static void testSelfDefineClassLoader() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyClassLoader loader = new MyClassLoader("my_loader1");
        loader.setPath("D:\\IDEA\\JavaCode\\testJVM\\target\\classes\\");
        Class<?> class1 = loader.loadClass("com.cyt.jvm.testClassLoader");
        System.out.println("class1 : " + class1.hashCode());//class1 : 1956725890
        Object obj = class1.newInstance();
        System.out.println(obj);//com.cyt.jvm.testClassLoader@677327b6
    }

}
class MyParent1{
    public static String str = "cyt";
    static {
        System.out.println("MyParent static block");
    }
}
class MyChild1 extends MyParent1{
    public static String str2 = "peace";
    static {
        System.out.println("MyChild static block");
    }
}
class MyParent2{
    public static final String str = "final_cyt";
    static {
        System.out.println("MyParent2 static block");
    }
}
class MyParent3{
    public static final String str = UUID.randomUUID().toString(); // 在運行期間才能隨機初始一個值
    static {
        System.out.println("MyParent3 static block");
    }
}
class MyParent4{
    static {
        System.out.println("MyParent4 static block");
    }
    {
        System.out.println("MyParent4 normal block");
    }
}
interface MyGrandpa_interface_5{
    public static final Thread thread = new Thread(){
        {
            System.out.println("MyGrandpa_interface_5 invoked");
        }
    };
}
interface MyParent_interface_5 extends MyGrandpa_interface_5{
    //父接口会被加载，但不会初始化
    public static final Thread thread = new Thread(){
        {
            System.out.println("MyParent_interface_5 invoked");
        }
    };
}
class MyParent_class_5 implements MyGrandpa_interface_5{
    public static final Thread thread = new Thread(){
        {
            System.out.println("MyParent_class_5 invoked");
        }
    };
}
class MyChild_class_5 extends MyParent_class_5{
    public static final int c = 5;
    public static int b = 6;
}
class MyChild_interface_5 implements MyParent_interface_5{
    public static int b = 6;
}
class Singleton {
    public static int counter1;
    private static Singleton singleton = new Singleton();

    private Singleton(){
        counter1++;
        counter2++; // 准备阶段会初始化为默认值
        System.out.println(counter1); //1
        System.out.println(counter2); //1
    }
    public static int counter2 = 0; // 0，按照顺序被再次初始化为 0
    public static Singleton getInstance(){
        return singleton;
    }
}

class C{
    static {
        System.out.println("class C static block");
    }
}

class MyParent6{
    public static int a = 2;
    static {
        System.out.println("MyParent6 static block");
    }
    static void doSomething(){
        System.out.println("static method");
    }
}
class MyChild6 extends MyParent6{
    public static int b = 3;
    static {
        System.out.println("MyChild6 static block");
    }
}
class MyClassLoader extends ClassLoader{
    private String classLoaderName;
    private final String fileExtension = ".class";
    private String path;

    MyClassLoader(String classLoaderName){
        super(); //将系统类加载器当做该类加载器的父加载器
        this.classLoaderName = classLoaderName;
    }
    MyClassLoader(ClassLoader parent, String classLoaderName){
        super(parent); // 显示指定该类加载器的父加载器
        this.classLoaderName = classLoaderName;
    }
    public void setPath(String path) {
        this.path = path;
    }
    protected Class<?> findClass(String className) throws ClassNotFoundException{
        System.out.println("findClass invoked : " + className);
        System.out.println("class loader name : " + this.classLoaderName);
        byte[] data = this.loadClassData(className);
        return this.defineClass(className, data, 0, data.length);
    }
    private byte[] loadClassData(String name){
        InputStream is = null;
        byte[] data = null;
        ByteArrayOutputStream baos = null;
        name = name.replace(".","\\");
        try {
            is = new FileInputStream(new File(this.path + name + this.fileExtension));
            baos = new ByteArrayOutputStream();
            int ch = 0;
            while (-1 != (ch = is.read())) {
                baos.write(ch);
            }
            data = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                baos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }
    public String toString(){
        return "[" + this.classLoaderName + "]";
    }
}
