package org.example.classLoad;

public class BootStrapClassLoad {

    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("BootStrap:"+String.class.getClassLoader());
        System.out.println(System.getProperty("sun.boot.class.path"));

        System.out.println("===========================================");
        System.out.println(System.getProperty("java.ext.dirs"));

        System.out.println("============================================");
        System.out.println(System.getProperty("java.class.path"));


        System.out.println("===============================================");
        Class<?> helloClass = Class.forName("Hello");
        System.out.println(helloClass.getClassLoader());
    }
}
