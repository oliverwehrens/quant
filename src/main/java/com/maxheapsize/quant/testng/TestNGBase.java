package com.maxheapsize.quant.testng;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public abstract class TestNGBase {

  private List<Method> publicVoidMethods = new ArrayList<Method>();

  public TestNGBase(Class klass) {
    publicVoidMethods = getPublicVoidMethods(klass);
  }

  protected final List<Method> getPublicVoidMethods(Class klass) {
    List<Method> result = new ArrayList<Method>();

    Method[] methods = klass.getDeclaredMethods();
    for (Method method : methods) {
      if (isPublicVoidMethod(method)) {
        result.add(method);
      }
    }
    return result;
  }

  private boolean isPublicVoidMethod(Method method) {
    return Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(Void.TYPE);
  }

  public List<Method> getPublicVoidMethods() {
    return publicVoidMethods;
  }

  public void setPublicVoidMethods(List<Method> publicVoidMethods) {
    this.publicVoidMethods = publicVoidMethods;
  }
}
