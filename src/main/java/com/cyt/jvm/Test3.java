package com.cyt.jvm;

public class Test3 {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Test1 loader = new Test1("loader_test3");
        loader.setPath("E:\\");
        Class<?> class1 = loader.loadClass("com.cyt.jvm.MyTest3");
//        Class<?> class2 = loader.loadClass("com.cyt.jvm.MyCat");
        System.out.println("class : " + class1.hashCode());
        Object obj = class1. newInstance();
//        Object obj2 = loader.loadClass("com.cyt.jvm.MyCat").newInstance();
        // 当没有指定 loader 的 path 时，会使用系统加载器
        /** 输出
         * class : 1735600054
         * MyTest3 Loaded : sun.misc.Launcher$AppClassLoader@18b4aac2
         * MyCat Loaded : sun.misc.Launcher$AppClassLoader@18b4aac2
         */
        // 当把当前路径下的 MyCat.class 和 MyTest3.class 剪切至其他路径，则均会使用 自定义类加载器
        /**
         * findClass invoked : com.cyt.jvm.MyTest3
         * class loader name : loader_test3
         * class : 2133927002
         * MyTest3 Loaded : [loader_test3]
         * findClass invoked : com.cyt.jvm.MyCat
         * class loader name : loader_test3
         * MyCat Loaded : [loader_test3]
         */
        // 只保留当前路径下的 MyTest3.class，其他路径保留 MyCat.class 和 MyTest3.class 时，MyTest3 使用系统类加载器， MyCat 会报错
        /**
         * class : 1735600054
         * MyTest3 Loaded : sun.misc.Launcher$AppClassLoader@18b4aac2
         * Exception in thread "main" java.lang.NoClassDefFoundError: com/cyt/jvm/MyCat
         */
        // 只保留当前路径下的 MyCat.class，其他路径保留 MyCat.class 和 MyTest3.class 时，MyTest3 使用自定义类加载器，MyCat 使用系统加载器
        // 这处与老师写的不同，根本原因在于 两个类都应该是 public 的，此处如果 MyCat 是 MyTest3 的外部类就会报错
        /**
         * findClass invoked : com.cyt.jvm.MyTest3
         * class loader name : loader_test3
         * class : 2133927002
         * MyTest3 Loaded : [loader_test3]
         * Test2 Loaded : sun.misc.Launcher$AppClassLoader@18b4aac2
         */
    }

}

