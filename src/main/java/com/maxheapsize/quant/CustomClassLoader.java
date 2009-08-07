package com.maxheapsize.quant;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;

public class CustomClassLoader extends ClassLoader {

  private String testClassPath;

  public CustomClassLoader(String testClassPath) {
    super(CustomClassLoader.class.getClassLoader());
    this.testClassPath = testClassPath + File.separator;
  }

  public Class loadClass(String className) throws ClassNotFoundException {
    return findClass(className);
  }

  public Class findClass(String className) {

    byte classByte[];
    Class result = null;
    result = (Class) classes.get(className);
    if (result != null) {
      return result;
    }

    try {
      return findSystemClass(className);
    }
    catch (Exception e) {
    }
    try {
      String classPath = testClassPath + className;
      String clazz = classPath.replace('.', '/') + ".class";
      classByte = loadClassData(clazz);
      result = defineClass(className, classByte, 0, classByte.length, null);
      classes.put(className, result);
      return result;
    }
    catch (Exception e) {
      return null;
    }
  }

  private byte[] loadClassData(String className) throws IOException {

    File f;
    f = new File(className);
    int size = (int) f.length();
    byte buff[] = new byte[size];
    FileInputStream fis = new FileInputStream(f);
    DataInputStream dis = new DataInputStream(fis);
    dis.readFully(buff);
    dis.close();
    return buff;
  }

  private Hashtable classes = new Hashtable();
}



