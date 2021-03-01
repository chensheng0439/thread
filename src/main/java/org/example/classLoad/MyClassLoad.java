package org.example.classLoad;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyClassLoad extends ClassLoader{

    private final static Path DEFAULT_CLASS_PATH = Paths.get("F:classloader1");
    private final Path classDir;

    // 使用默认的class路径
    public MyClassLoad() {
        super();
        this.classDir = DEFAULT_CLASS_PATH;
    }

    //允许传入指定的class
    public MyClassLoad(String classDir) {
        super();
        this.classDir = Paths.get(classDir);
    }

    public MyClassLoad(String classDir,ClassLoader parent){
        super(parent);
        this.classDir = Paths.get(classDir);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classBytes = this.readClassBytes(name);
        if(classBytes == null || classBytes.length == 0){
            throw new ClassNotFoundException("Can not load the class "+name);
        }
        return this.defineClass(name,classBytes,0,classBytes.length);
    }

    private byte[] readClassBytes(String name) throws ClassNotFoundException {
        String classPath = name.replace(".","/");
        Path classFullPath = classDir.resolve(Paths.get(classPath + ".class"));
        if(!classFullPath.toFile().exists()){
            throw new ClassNotFoundException("The Class "+ name +" not found.");
        }
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream() ){
            Files.copy(classFullPath,baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new ClassNotFoundException("load the class "+ name +" occur error.",e);
        }
    }

    @Override
    public String toString() {
        return "My ClassLoader";
    }
}
