package com.maxheapsize.quant;

import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;

public class CustomClassLoader extends ClassLoader {

  private String testClassPath;
  private Hashtable classes = new Hashtable();
  private static Logger log = Logger.getLogger(CustomClassLoader.class);


  public CustomClassLoader(String testClassPath) {
    super(CustomClassLoader.class.getClassLoader());
    this.testClassPath = testClassPath + File.separator;
    File f = new File(this.testClassPath);
    log.debug("Custom ClassPath "+f.getAbsolutePath());
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
      // did not find the class          
    }
    try {
      String classPath = testClassPath + className;
      String clazz = classPath.replace('.', '/') + ".class";
      log.debug("Clazz "+clazz);
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
    File file;
    file = new File(className);
    log.debug("Trying to load "+className);
    int size = (int) file.length();
    byte buffer[] = new byte[size];
    DataInputStream dataInputStream = null;
    try {
      FileInputStream fileInputStream = new FileInputStream(file);
      dataInputStream = new DataInputStream(fileInputStream);
      dataInputStream.readFully(buffer);
    }
    finally {
      if (dataInputStream != null) {
        dataInputStream.close();
      }
    }
    return buffer;
  }
}



