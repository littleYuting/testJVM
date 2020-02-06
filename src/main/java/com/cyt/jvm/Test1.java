package com.cyt.jvm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Test1 extends ClassLoader{
    private String classLoaderName;
    private final String fileExtension = ".class";
    private String path;

    public Test1(String classLoaderName){
        super(); //将系统类加载器当做该类加载器的父加载器
        this.classLoaderName = classLoaderName;
    }
    Test1(ClassLoader parent, String classLoaderName){
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

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Test1 loader = new Test1("my_loader1");
//        loader.setPath("D:\\IDEA\\JavaCode\\testJVM\\target\\classes\\");
        loader.setPath("E:\\");
        Class<?> class1 = loader.loadClass("com.cyt.jvm.Test2");
        System.out.println("class1 : " + class1.hashCode());
        Object obj = class1.newInstance();
        System.out.println(obj);

        Test1 loader2 = new Test1("my_loader2");
        // 若指定其父加载器，则真正加载类的是 loader，两个 class 的 hashCode 一致【双亲委托机制】
//        Test1 loader2 = new Test1(loader, "my_loader2");
        loader2.setPath("E:\\");
        Class<?> class2 = loader2.loadClass("com.cyt.jvm.Test2");
        System.out.println("class2 : " + class2.hashCode());
        Object obj2 = class2.newInstance();
        System.out.println(obj2);


        /** 输出
         * findClass invoked : com.cyt.jvm.Test2
         * class loader name : my_loader1
         * class1 : 21685669
         * com.cyt.jvm.Test2@7f31245a
         * findClass invoked : com.cyt.jvm.Test2
         * class loader name : my_loader2
         * class2 : 1173230247
         * com.cyt.jvm.Test2@330bedb4
         */
        // 说明，如果 Test2 在当前文件路径存在，则调用的是 系统加载器。两个 class 的 hashCode 一致，这是在同一命名空间下，只会同一个类只会加载一次
        // 但若是自定义加载器，则两个 class 的 hashcode 不同，这是因为在不同命名空间下，可能存在不同的两个类
    }
}

