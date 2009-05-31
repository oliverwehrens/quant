package com.maxheapsize.quant.testng;

import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

public class TestNGDisabledTestFinder extends TestNGBase implements com.maxheapsize.quant.DisabledTestFinder {

  private List<Method> disabledTests = new ArrayList<Method>();

  public static class Builder {
    private Class klass;

    public Builder(Class klass) {
      super();
      this.klass = klass;
    }

    public com.maxheapsize.quant.DisabledTestFinder build() {
      return new TestNGDisabledTestFinder(this);
    }
  }

  private TestNGDisabledTestFinder(Builder builder) {
    super(builder.klass);
    examineClass();
  }

  public boolean hasDisabledTests() {
    return !disabledTests.isEmpty();
  }

  public List<Method> getDisabledTests() {
    return disabledTests;
  }

  private void examineClass() {
    searchForDisabledTests();
  }

  private void searchForDisabledTests() {
    for (Method method : publicVoidMethods) {
      Annotation[] annotations = method.getAnnotations();
      for (Annotation annotation : annotations) {
        if (isTestAnnotation(annotation)) {
          Test testAnnotation = (Test) annotation;
          if (!testAnnotation.enabled()) {
            disabledTests.add(method);
          }
        }
      }
    }
  }
}
