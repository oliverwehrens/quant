package com.maxheapsize.quant.testng;

import com.maxheapsize.quant.ClassTester;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class TestNGClassTester extends TestNGBase implements ClassTester {

  private boolean validTestAnnotationWithTestGroupOnClass;

  private Class klass;
  private List<String> validTestGroups = new ArrayList<String>();
  private List<Method> nonTestAnnotatedPublicVoidMethods = new ArrayList<Method>();
  private List<Method> methodsWithWrongTestGroup = new ArrayList<Method>();
  private boolean useOnlyAnnotatedMethods = false;
  private boolean ignoreAbstractClass = true;

  // Builder

  public static class Builder {
    private final Class klass;
    private List<String> validTestGroups = new ArrayList<String>();
    private boolean useOnlyAnnotatedMethods = false;
    private boolean ignoreAbstractClass = true;

    /**
     * Constructs a ClassTester which will examine the given class.
     *
     * @param klass Class to be examined.
     */
    public Builder(Class klass) {
      super();
      this.klass = klass;
    }

    /**
     * Add a test group to the pool of test groups which all test methods should belong to.
     *
     * @param testGroupName
     *
     * @return Builder
     */
    public Builder addTestGroup(String testGroupName) {
      validTestGroups.add(testGroupName);
      return this;
    }

    /**
     * Only check methods which are annotated. Do not report methods which are public void but do not have a @Test annotation.
     *
     * @return Builder
     */
    public Builder useOnlyAnnotatedMethods() {
      useOnlyAnnotatedMethods = true;
      return this;
    }

    /**
     * Also run the checks against abstract classes.
     *
     * @return Builder
     */
    public Builder doNotIgnoreAbstractClass() {
      ignoreAbstractClass = false;
      return this;
    }

    /**
     * Build the ClassTester
     *
     * @return ClassTester
     */
    public ClassTester build() {
      return new TestNGClassTester(this);
    }
  }

  /**
   * Creates a Builder to create the ClassTester.
   *
   * @param klass Class to be examined.
   *
   * @return Builder
   */
  public static Builder createBuilder(Class klass) {
    return new Builder(klass);
  }

  private TestNGClassTester(Builder builder) {
    super(builder.klass);
    this.klass = builder.klass;
    this.validTestGroups = builder.validTestGroups;
    this.useOnlyAnnotatedMethods = builder.useOnlyAnnotatedMethods;
    this.ignoreAbstractClass = builder.ignoreAbstractClass;
    examineClass();
  }

  private void examineClass() {
    publicVoidMethods = getPublicVoidMethods(klass);
    nonTestAnnotatedPublicVoidMethods = getNonTestAnnotatedPublicVoidMethod(publicVoidMethods);
    validTestAnnotationWithTestGroupOnClass = checkForTestAnnotationWithValidTestGroupOnClass();
    methodsWithWrongTestGroup = checkMethodsToConfirmToSpecification();
  }

  // Public methods

  public boolean isInvalidTestClass() {
    return !(allTestMethodsHaveValidTestGroup() || (isAbstractClass() && ignoreAbstractClass));
  }

  public String toString() {
    StringBuffer result = new StringBuffer();
    result.append("Report for Class ").append(klass.getName());
    result.append("\n----------------------------\n");
    result.append(reportMethods("Public void methods", publicVoidMethods));
    result.append(reportMethods("Non TestAnnotated methods", nonTestAnnotatedPublicVoidMethods));
    result.append("* Test annotation with TestGroups on Class: ").append(Boolean.valueOf(validTestAnnotationWithTestGroupOnClass)).append("\n");
    result.append("* Allowed TestGroups : ");
    for (String validTestGroup : validTestGroups) {
      result.append(" + " + validTestGroup);
    }
    result.append("\n");
    return result.toString();
  }

  // Private Methods

  private boolean isAbstractClass() {
    int modifier = klass.getModifiers();
    return Modifier.isAbstract(modifier);
  }

  private boolean allTestMethodsHaveValidTestGroup() {
    if (validTestAnnotationWithTestGroupOnClass) {
      return true;
    }
    else if (methodsWithWrongTestGroup.isEmpty()) {
      return true;
    }
    return false;
  }

  private List<Method> checkMethodsToConfirmToSpecification() {
    List<Method> result = new ArrayList<Method>();
    for (Method publicVoidMethod : publicVoidMethods) {
      if (!methodConfirmsToSpecification(publicVoidMethod)) {
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

  private boolean methodConfirmsToSpecification(Method method) {
    Annotation[] annotations = method.getAnnotations();
    boolean hasTestAnnotations = false;
    for (Annotation annotation : annotations) {

      if (isTestAnnotation(annotation)) {
        hasTestAnnotations = true;
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
    if (!hasTestAnnotations && useOnlyAnnotatedMethods) {
      return true;
    }
    return false;
  }

  private String[] getTestGroupsFromAnnotation(Annotation annotation) {

    String[] testAnnotationGroups = {};
    if (annotation.annotationType().equals(Test.class)) {
      testAnnotationGroups = ((Test) annotation).groups();
    }

    return testAnnotationGroups;
  }
}
