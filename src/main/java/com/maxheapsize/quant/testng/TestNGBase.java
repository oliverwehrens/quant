package com.maxheapsize.quant.testng;

import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public abstract class TestNGBase {

  protected List<Method> publicVoidMethods = new ArrayList<Method>();

  boolean isTestAnnotation(Annotation annotation) {
    return annotation.annotationType().equals(Test.class);
  }

  public TestNGBase(Class klass) {
    super();
    publicVoidMethods = getPublicVoidMethods(klass);
  }

  List<Method> getPublicVoidMethods(Class klass) {
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
}
