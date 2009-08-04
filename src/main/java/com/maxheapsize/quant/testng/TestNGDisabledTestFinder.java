package com.maxheapsize.quant.testng;

import com.maxheapsize.quant.DisabledTestFinder;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestNGDisabledTestFinder extends TestNGBase implements DisabledTestFinder {

  private List<Method> disabledTests = new ArrayList<Method>();

  private String testClassName;

  public static class Builder {

    private Class klass;

    public Builder(Class klass) {
      super();
      this.klass = klass;
    }

    public DisabledTestFinder build() {
      return new TestNGDisabledTestFinder(this);
    }

    protected String getTestClassName() {
      return klass.getName();
    }
  }

  public static Builder createBuilder(Class klass) {
    return new Builder(klass);
  }

  private TestNGDisabledTestFinder(Builder builder) {
    super(builder.klass);
    testClassName = builder.getTestClassName();
    examineClass();
  }

  public boolean hasDisabledTests() {
    return !disabledTests.isEmpty();
  }

  public List<Method> getDisabledTests() {
    return disabledTests;
  }

  public String getTestClassName() {
    return testClassName;
  }

  private void examineClass() {
    searchForDisabledTests();
  }

  private void searchForDisabledTests() {
    for (Method method : getPublicVoidMethods()) {
      Annotation[] annotations = method.getAnnotations();
      for (Annotation annotation : annotations) {
        if (annotation.annotationType().equals(Test.class)) {
          Test testAnnotation = (Test) annotation;
          if (!testAnnotation.enabled()) {
            disabledTests.add(method);
          }
        }
      }
    }
  }
}
