package com.maxheapsize.quant.testng;

import com.maxheapsize.quant.ClassTester;
import com.maxheapsize.quant.ClassTesterException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public final class TestNGClassTester extends TestNGBase implements ClassTester {

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
    public final Builder addTestGroup(String testGroupName) {
      validTestGroups.add(testGroupName);
      return this;
    }

    /**
     * Only check methods which are annotated. Do not report methods which are public void but do not have a @Test annotation.
     *
     * @return Builder
     */
    public final Builder useOnlyAnnotatedMethods() {
      useOnlyAnnotatedMethods = true;
      return this;
    }

    /**
     * Also run the checks against abstract classes.
     *
     * @return Builder
     */
    public final Builder doNotIgnoreAbstractClass() {
      ignoreAbstractClass = false;
      return this;
    }

    /**
     * Sets the tests groups which the tests need to be in.
     *
     * @param groupNames names of the testgroups
     *
     * @return Builder
     */
    public final Builder setTestGroups(List<String> groupNames) {
      validTestGroups = groupNames;
      return this;
    }

    /**
     * Build the ClassTester
     *
     * @return ClassTester
     */
    public final ClassTester build() {
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
    setPublicVoidMethods(getPublicVoidMethods(klass));
    nonTestAnnotatedPublicVoidMethods = getNonTestAnnotatedPublicVoidMethod(getPublicVoidMethods());
    validTestAnnotationWithTestGroupOnClass = checkForTestAnnotationWithValidTestGroupOnClass();
    methodsWithWrongTestGroup = checkMethodsToConfirmToSpecification();
  }

  // Public methods

  public boolean isInvalidTestClass() {
    return !(allTestMethodsHaveValidTestGroup() || (isAbstractClass() && ignoreAbstractClass));
  }

  public boolean isValidTestClass() {
    return !isInvalidTestClass();
  }

  public String reportViolation() {
    StringBuffer result = new StringBuffer();
    result.append("\nReport for Class ").append(klass.getName());
    result.append("\n");
    result.append("Ignore abstract classes: ").append(ignoreAbstractClass);
    result.append("\n");
    if (!validTestGroups.isEmpty()) {
      result.append("Specified TestGroups : ");
      for (String validTestGroup : validTestGroups) {
        result.append(" + ").append(validTestGroup);
      }
      result.append("\n");
    }

    if (!nonTestAnnotatedPublicVoidMethods.isEmpty()) {
      result.append(reportMethods("Non TestAnnotated methods", nonTestAnnotatedPublicVoidMethods));
    }
    if (!methodsWithWrongTestGroup.isEmpty()) {
      result.append(reportMethods("Methods with wrong test group: ", methodsWithWrongTestGroup));
    }

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
    for (Method publicVoidMethod : getPublicVoidMethods()) {
      if (!methodConfirmsToSpecification(publicVoidMethod)) {
        result.add(publicVoidMethod);
      }
    }
    return result;
  }

  private boolean checkForTestAnnotationWithValidTestGroupOnClass() {

    Annotation[] annotations = klass.getAnnotations();
    for (Annotation annotation : annotations) {

      if (checkTestAnnotationForTestGroups(annotation)) {
        return true;
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
    Annotation[] annotations = method.getAnnotations();
    for (Annotation annotation : annotations) {
      if (isTestAnnotation(annotation)) {
        return true;
      }
    }
    return false;
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
          if (testAnnotationGroups.length == 0) {
            return false;
          }
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
    for (Class testNGAnnotationClass : getAnnotations().keySet()) {
      if (annotation.annotationType().equals(testNGAnnotationClass)) {
        testAnnotationGroups = getTestGroups(annotation, testNGAnnotationClass);
      }
    }
    return testAnnotationGroups;
  }

  private String[] getTestGroups(Annotation annotation, Class annotationTypeKlass) {

    Method groupsMethod = null;
    String[] testAnnotationGroups = new String[0];
    try {
      groupsMethod = annotationTypeKlass.getMethod("groups", null);

      testAnnotationGroups = (String[]) groupsMethod.invoke(annotation);
    }
    catch (NoSuchMethodException e) {
      throw new ClassTesterException("Could not get groups of annotation " + annotation.annotationType().getName());
    }
    catch (IllegalAccessException e) {
      throw new ClassTesterException("Could not get groups of annotation " + annotation.annotationType().getName());
    }
    catch (InvocationTargetException e) {
      throw new ClassTesterException("Could not get groups of annotation " + annotation.annotationType().getName());
    }
    return testAnnotationGroups;
  }
}
