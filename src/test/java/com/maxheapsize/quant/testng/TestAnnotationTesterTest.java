package com.maxheapsize.quant.testng;

import com.maxheapsize.quant.ClassTester;
import com.maxheapsize.quant.testclasses.*;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

@Test(groups = "unitTest")
public class TestAnnotationTesterTest {

  ClassTester unitUnderTest;
  public static final String TESTGROUP_UNITTEST = "testUnitTest";
  public static final String TESTGROUP_GROUPONE = "testGroup1";
  public static final String TESTGROUP_GROUPTWO = "testGroup2";
  public static final String TESTGROUP_WRONG = "testWrongGroup";

  @Test
  public void testAnnotationOnClass() {
    unitUnderTest = TestNGClassTester.createBuilder(AnnotationOnlyOnClassWithTestGroup.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertFalse(unitUnderTest.isInvalidTestClass());
  }

  @Test
  public void testNoAnnotation() {
    unitUnderTest = TestNGClassTester.createBuilder(NoAnnotation.class).build();
    assertTrue(unitUnderTest.isInvalidTestClass());
  }

  @Test
  public void testWrongTestGroupOnMethod() {
    unitUnderTest = TestNGClassTester.createBuilder(WrongTestGroupOnMethod.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertTrue(unitUnderTest.isInvalidTestClass(), unitUnderTest.reportViolation());
  }

  @Test
  public void testWrongTestGroupOnClass() {
    unitUnderTest = TestNGClassTester.createBuilder(WrongTestGroupOnClass.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertTrue(unitUnderTest.isInvalidTestClass());
  }

  @Test
  public void testValidTestGroup() {
    unitUnderTest = TestNGClassTester.createBuilder(TestAnnotationOnlyOnMethod.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertFalse(unitUnderTest.isInvalidTestClass());
  }

  @Test
  public void testTwoValidTestGroups() {
    unitUnderTest = TestNGClassTester.createBuilder(TwoTestGroups.class).addTestGroup(TESTGROUP_GROUPONE).addTestGroup(TESTGROUP_GROUPTWO).build();
    assertFalse(unitUnderTest.isInvalidTestClass());
  }

  @Test
  public void testOneMethodWithTestGroupSecondWithout() {
    unitUnderTest = TestNGClassTester.createBuilder(OneMethodWithTestGroupSecondWithout.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertTrue(unitUnderTest.isInvalidTestClass());
  }

  @Test(timeOut = 1000)
  public void testPerformance() {
    for (int i = 0; i < 10000; i++) {
      ClassTester performance = TestNGClassTester.createBuilder(OneMethodWithTestGroupSecondWithout.class).addTestGroup(TESTGROUP_UNITTEST).build();
    }
  }

  @Test
  public void testUseOnlyAnntatedMethods() {
    unitUnderTest = TestNGClassTester.createBuilder(OnlyOneMethodWithTest.class).useOnlyAnnotatedMethods().build();
    assertFalse(unitUnderTest.isInvalidTestClass());
  }

  @Test
  public void testDoNotIgnoreAbstractClass() {
    unitUnderTest = TestNGClassTester.createBuilder(AbstractTestClass.class).doNotIgnoreAbstractClass().build();
    assertTrue(unitUnderTest.isInvalidTestClass());
  }

  @Test
  public void testIgnoreAbstractClass() {
    unitUnderTest = TestNGClassTester.createBuilder(AbstractTestClass.class).build();
    assertFalse(unitUnderTest.isInvalidTestClass());
  }

  @Test
  public void testAllTestNGAnnotationsCountAsTestMethod() {
    unitUnderTest = TestNGClassTester.createBuilder(CheckAllTestNGAnnotationsWithOutGroups.class).build();
    assertFalse(unitUnderTest.isInvalidTestClass());
  }

  @Test
  public void testFindSetupMethodWithoutGroup() {
    unitUnderTest = TestNGClassTester.createBuilder(SetupMethodWithoutTestGroup.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertTrue(unitUnderTest.isInvalidTestClass());
  }

  @Test
  public void testNonAtTestAnnotationWithGroup() {
    unitUnderTest = TestNGClassTester.createBuilder(NonAtTestAnnotationWithGroup.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertFalse(unitUnderTest.isInvalidTestClass());
  }

  @Test
  public void testNoGroupOnAnnotationsJustOnClassWithDiffeentAnnotations() {
    unitUnderTest = TestNGClassTester.createBuilder(NoGroupOnTestsJustOnClassWithDifferentAnnotations.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertFalse(unitUnderTest.isInvalidTestClass());
  }

  @Test
  public void testAnnotationOnlyOnTestClassWithoutTestGroup() {
    unitUnderTest = TestNGClassTester.createBuilder(AnnotationOnlyOnTestClassWithoutTestGroup.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertTrue(unitUnderTest.isInvalidTestClass());
  }
}
