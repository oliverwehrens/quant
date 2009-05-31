package com.maxheapsize.quant.testng;

import com.maxheapsize.quant.ClassTester;
import com.maxheapsize.quant.testclasses.*;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

@Test(groups = "unitTest")
public class TestAnnotationTesterTest {

  ClassTester unitUnder;
  public static final String TESTGROUP_UNITTEST = "testUnitTest";
  public static final String TESTGROUP_GROUPONE = "testGroup1";
  public static final String TESTGROUP_GROUPTWO = "testGroup2";
  public static final String TESTGROUP_WRONG = "testWrongGroup";

  @Test
  public void testAnnotationOnClass() {
    unitUnder = new TestNGClassTester.Builder(AnnotationOnlyOnClassWithTestGroup.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertTrue(unitUnder.allTestMethodsHaveValidTestGroup());
  }

  @Test
  public void testNoAnnotation() {
    unitUnder = new TestNGClassTester.Builder(NoAnnotation.class).build();
    assertFalse(unitUnder.allTestMethodsHaveValidTestGroup());
  }

  @Test
  public void testWrongTestGroupOnMethod() {
    unitUnder = new TestNGClassTester.Builder(WrongTestGroupOnMethod.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertFalse(unitUnder.allTestMethodsHaveValidTestGroup());
  }

  @Test
  public void testWrongTestGroupOnClass() {
    unitUnder = new TestNGClassTester.Builder(WrongTestGroupOnClass.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertFalse(unitUnder.allTestMethodsHaveValidTestGroup());
  }

  @Test
  public void testValidTestGroup() {
    unitUnder = new TestNGClassTester.Builder(TestAnnotationOnlyOnMethod.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertTrue(unitUnder.allTestMethodsHaveValidTestGroup());
  }

  @Test
  public void testTwoValidTestGroups() {
    unitUnder = new TestNGClassTester.Builder(TwoTestGroups.class).addTestGroup(TESTGROUP_GROUPONE).addTestGroup(TESTGROUP_GROUPTWO).build();
    assertTrue(unitUnder.allTestMethodsHaveValidTestGroup());
  }

  @Test
  public void testOneMethodWithTestGroupSecondWithout() {
    unitUnder = new TestNGClassTester.Builder(OneMethodWithTestGroupSecondWithout.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertFalse(unitUnder.allTestMethodsHaveValidTestGroup());
  }

  @Test(timeOut = 1000)
  public void testPerformance() {
    for (int i = 0; i < 10000; i++) {
      com.maxheapsize.quant.ClassTester performance = new TestNGClassTester.Builder(OneMethodWithTestGroupSecondWithout.class).addTestGroup(TESTGROUP_UNITTEST).build();
    }
  }
}
