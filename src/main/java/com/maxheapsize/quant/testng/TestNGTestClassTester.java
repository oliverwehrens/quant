package com.maxheapsize.quant.testng;

import com.maxheapsize.quant.TestClassTester;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

public class TestNGTestClassTester extends TestNGBase implements TestClassTester {

  private boolean validTestAnnotationWithTestGroupOnClass;

  private Class klass;
  private List<String> validTestGroups = new ArrayList<String>();
  private List<Method> nonTestAnnotatedPublicVoidMethods = new ArrayList<Method>();
  private List<Method> methodsWithWrongTestGroup = new ArrayList<Method>();

  // Builder

  public static class Builder {
    private final Class klass;
    private List<String> validTestGroups = new ArrayList<String>();

    public Builder(Class klass) {
      super();
      this.klass = klass;
    }

    public Builder addTestGroup(String testGroupName) {
      validTestGroups.add(testGroupName);
      return this;
    }

    public TestClassTester build() {
      return new TestNGTestClassTester(this);
    }
  }

  private TestNGTestClassTester(Builder builder) {
    super(builder.klass);
    this.klass = builder.klass;
    this.validTestGroups = builder.validTestGroups;
    examineClass();
  }

  // Public methods

  public boolean allTestMethodsHaveValidTestGroup() {
    if (validTestAnnotationWithTestGroupOnClass) {
      return true;
    }
    else if (methodsWithWrongTestGroup.isEmpty()) {
      return true;
    }
    return false;
  }

  public String toString() {
    StringBuffer result = new StringBuffer();
    result.append("Report for Class ").append(klass.getName());
    result.append("\n----------------------------\n");
    result.append(reportMethods("Public void methods", publicVoidMethods));
    result.append(reportMethods("Non TestAnnotated methods", nonTestAnnotatedPublicVoidMethods));
    result.append("* Test annotation with TestGroups on Class: " + new Boolean(validTestAnnotationWithTestGroupOnClass)).append("\n");
    return result.toString();
  }

  // Private Methods

  private void examineClass() {
    publicVoidMethods = getPublicVoidMethods(klass);
    nonTestAnnotatedPublicVoidMethods = getNonTestAnnotatedPublicVoidMethod(publicVoidMethods);
    validTestAnnotationWithTestGroupOnClass = checkForTestAnnotationWithValidTestGroupOnClass();
    methodsWithWrongTestGroup = checkForMethodsWithWrongTestGroup();
  }

  private List<Method> checkForMethodsWithWrongTestGroup() {
    List<Method> result = new ArrayList<Method>();
    for (Method publicVoidMethod : publicVoidMethods) {
      if (!hasTestAnnotationWithValidTestGroup(publicVoidMethod)) {
        result.add(publicVoidMethod);
      }
    }
    return result;
  }

  private boolean checkForTestAnnotationWithValidTestGroupOnClass() {
    if (klass.isAnnotationPresent(Test.class)) {
      Annotation[] annotations = klass.getAnnotations();
      for (Annotation annotation : annotations) {
        if (checkTestAnnotationForTestGroups(annotation)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean checkTestAnnotationForTestGroups(Annotation annotation) {
    if (isTestAnnotation(annotation)) {
      String[] testGroupsOnAnnotation = getTestGroupsFromAnnotation(annotation);
      for (String group : testGroupsOnAnnotation) {
        if (testAnnotationGroupIsInExpectedTestGroup(group) || expectedTestGroupIsEmpty()) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean expectedTestGroupIsEmpty() {
    return validTestGroups.isEmpty();
  }

  private boolean testAnnotationGroupIsInExpectedTestGroup(String group) {
    return validTestGroups.contains(group);
  }

  private String reportMethods(String header, List<Method> methods) {
    StringBuffer result = new StringBuffer();
    result.append("* ").append(header).append("\n");
    for (Method method : methods) {
      result.append("  - ").append(method.getName()).append("\n");
    }
    return result.toString();
  }

  private List<Method> getNonTestAnnotatedPublicVoidMethod(Iterable<Method> methodIterable) {
    List<Method> result = new ArrayList<Method>();
    for (Method method : methodIterable) {
      boolean addMethod = true;
      if (hasTestAnnotation(method)) {
        addMethod = false;
      }
      if (addMethod) {
        result.add(method);
      }
    }
    return result;
  }

  private boolean hasTestAnnotation(Method method) {
    return method.isAnnotationPresent(Test.class);
  }

  private boolean hasTestAnnotationWithValidTestGroup(Method method) {
    Annotation[] annotations = method.getAnnotations();
    for (Annotation annotation : annotations) {
      if (isTestAnnotation(annotation)) {
        if (expectedTestGroupIsEmpty()) {
          return true;
        }
        else {
          String[] testAnnotationGroups = getTestGroupsFromAnnotation(annotation);
          for (String testGroup : testAnnotationGroups) {
            if (testAnnotationGroupIsInExpectedTestGroup(testGroup)) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  private String[] getTestGroupsFromAnnotation(Annotation annotation) {
    Test testAnnotation = (Test) annotation;
    String[] testAnnotationGroups = testAnnotation.groups();
    return testAnnotationGroups;
  }
}
