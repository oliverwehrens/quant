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
    unitUnder = TestNGClassTester.createBuilder(AnnotationOnlyOnClassWithTestGroup.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertTrue(unitUnder.allTestMethodsHaveValidTestGroup());
  }

  @Test
  public void testNoAnnotation() {
    unitUnder = TestNGClassTester.createBuilder(NoAnnotation.class).build();
    assertFalse(unitUnder.allTestMethodsHaveValidTestGroup());
  }

  @Test
  public void testWrongTestGroupOnMethod() {
    unitUnder = TestNGClassTester.createBuilder(WrongTestGroupOnMethod.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertFalse(unitUnder.allTestMethodsHaveValidTestGroup());
  }

  @Test
  public void testWrongTestGroupOnClass() {
    unitUnder = TestNGClassTester.createBuilder(WrongTestGroupOnClass.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertFalse(unitUnder.allTestMethodsHaveValidTestGroup());
  }

  @Test
  public void testValidTestGroup() {
    unitUnder = TestNGClassTester.createBuilder(TestAnnotationOnlyOnMethod.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertTrue(unitUnder.allTestMethodsHaveValidTestGroup());
  }

  @Test
  public void testTwoValidTestGroups() {
    unitUnder = TestNGClassTester.createBuilder(TwoTestGroups.class).addTestGroup(TESTGROUP_GROUPONE).addTestGroup(TESTGROUP_GROUPTWO).build();
    assertTrue(unitUnder.allTestMethodsHaveValidTestGroup());
  }

  @Test
  public void testOneMethodWithTestGroupSecondWithout() {
    unitUnder = TestNGClassTester.createBuilder(OneMethodWithTestGroupSecondWithout.class).addTestGroup(TESTGROUP_UNITTEST).build();
    assertFalse(unitUnder.allTestMethodsHaveValidTestGroup());
  }

  @Test(timeOut = 1000)
  public void testPerformance() {
    for (int i = 0; i < 10000; i++) {
      com.maxheapsize.quant.ClassTester performance = TestNGClassTester.createBuilder(OneMethodWithTestGroupSecondWithout.class).addTestGroup(TESTGROUP_UNITTEST).build();
    }
  }
}
