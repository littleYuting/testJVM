package com.cyt.jvm.ClassLoader;

public class Test6 implements Runnable {
    private Thread thread;
    public Test6(){
        thread = new Thread(this);
        thread.start();
    }
    public void run() {
        ClassLoader loader = this.thread.getContextClassLoader();
        this.thread.setContextClassLoader(loader);

        System.out.println("class : " + loader.getClass());
        System.out.println("Parent : " + loader.getParent().getClass());
    }

    public static void main(String[] args) {
        new Test6();
        /** 输出
         * class : class sun.misc.Launcher$AppClassLoader
         * Parent : class sun.misc.Launcher$ExtClassLoader
         */
    }
}
