package com.maxheapsize.quant.testng;

import com.maxheapsize.quant.TestClassTester;
import com.maxheapsize.quant.testclasses.*;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

@Test(groups = "unitTest")
public class TestAnnotationTesterTest {

  TestClassTester unitUnderTest;
  public static final String TESTGROUP_UNITTEST = "testUnitTest";
  public static final String TESTGROUP_GROUPONE = "testGroup1";
  public static final String TESTGROUP_GROUPTWO = "testGroup2";
  public static final String TESTGROUP_WRONG = "testWrongGroup";

  @Test
  public void testAnnotationOnClass() {
    unitUnderTest = new TestNGTestClassTester.Builder(AnnotationOnlyOnClassWithTestGroup.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertTrue(unitUnderTest.allTestMethodsHaveValidTestGroup());
  }

  @Test
  public void testNoAnnotation() {
    unitUnderTest = new TestNGTestClassTester.Builder(NoAnnotation.class).build();
    assertFalse(unitUnderTest.allTestMethodsHaveValidTestGroup());
  }

  @Test
  public void testWrongTestGroupOnMethod() {
    unitUnderTest = new TestNGTestClassTester.Builder(WrongTestGroupOnMethod.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertFalse(unitUnderTest.allTestMethodsHaveValidTestGroup());
  }

  @Test
  public void testWrongTestGroupOnClass() {
    unitUnderTest = new TestNGTestClassTester.Builder(WrongTestGroupOnClass.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertFalse(unitUnderTest.allTestMethodsHaveValidTestGroup());
  }

  @Test
  public void testValidTestGroup() {
    unitUnderTest = new TestNGTestClassTester.Builder(TestAnnotationOnlyOnMethod.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertTrue(unitUnderTest.allTestMethodsHaveValidTestGroup());
  }

  @Test
  public void testTwoValidTestGroups() {
    unitUnderTest = new TestNGTestClassTester.Builder(TwoTestGroups.class).addTestGroup(TESTGROUP_GROUPONE).addTestGroup(TESTGROUP_GROUPTWO).build();
    assertTrue(unitUnderTest.allTestMethodsHaveValidTestGroup());
  }

  @Test
  public void testOneMethodWithTestGroupSecondWithout() {
    unitUnderTest = new TestNGTestClassTester.Builder(OneMethodWithTestGroupSecondWithout.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertFalse(unitUnderTest.allTestMethodsHaveValidTestGroup());
  }

  @Test(timeOut = 1000)
  public void testPerformance() {
    for (int i = 0; i < 10000; i++) {
      TestClassTester performanceTest = new TestNGTestClassTester.Builder(OneMethodWithTestGroupSecondWithout.class).addTestGroup(TESTGROUP_UNITTEST).build();
    }
  }
}
