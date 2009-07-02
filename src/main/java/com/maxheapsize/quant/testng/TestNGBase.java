package com.maxheapsize.quant.testng;

import org.testng.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public abstract class TestNGBase {

  private List<Method> publicVoidMethods = new ArrayList<Method>();

  private Map<Class, String> annotations = new HashMap<Class, String>();

  final boolean isTestAnnotation(Annotation annotation) {
    Class annotationType = annotation.annotationType();
    return annotations.containsKey(annotationType);
  }

  public TestNGBase(Class klass) {
    super();
    storeTestAnnotation(BeforeSuite.class);
    storeTestAnnotation(AfterSuite.class);
    storeTestAnnotation(BeforeTest.class);
    storeTestAnnotation(AfterTest.class);
    storeTestAnnotation(BeforeGroups.class);
    storeTestAnnotation(AfterGroups.class);
    storeTestAnnotation(BeforeClass.class);
    storeTestAnnotation(AfterClass.class);
    storeTestAnnotation(BeforeMethod.class);
    storeTestAnnotation(AfterMethod.class);
    storeTestAnnotation(Test.class);

    publicVoidMethods = getPublicVoidMethods(klass);
  }

  private void storeTestAnnotation(Class testAnnotation) {
    annotations.put(testAnnotation, testAnnotation.getName());
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

  public Map<Class, String> getAnnotations() {
    return annotations;
  }

  public void setAnnotations(Map<Class, String> annotations) {
    this.annotations = annotations;
  }
}
