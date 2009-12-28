package com.maxheapsize.quant.testng;

import com.maxheapsize.quant.ClassTester;
import com.maxheapsize.quant.testclasses.AbstractTestClass;
import com.maxheapsize.quant.testclasses.OneMethodWithTestGroupSecondWithout;
import com.maxheapsize.quant.testclasses.OnlyOneMethodWithTest;
import com.maxheapsize.quant.testclasses.TestMultipleTestGroups;
import com.maxheapsize.quant.testclasses.TwoTestGroups;
import com.maxheapsize.quant.testgroups.TestGroupTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;

@Test(groups = "unitTest")
public class TestAnnotationTesterTest {

  private ClassTester unitUnderTest;
  public static final String TESTGROUP_UNITTEST = "testUnitTest";
  public static final String TESTGROUP_GROUPONE = "testGroup1";
  public static final String TESTGROUP_GROUPTWO = "testGroup2";
  public static final String TESTGROUP_WRONG = "testWrongGroup";

  @Test
  public void testTwoValidTestGroups() {
    unitUnderTest = TestNGClassTester.createBuilder(TwoTestGroups.class).addTestGroup(TESTGROUP_GROUPONE).addTestGroup(TESTGROUP_GROUPTWO).build();
    assertTrue(unitUnderTest.isValidTestClass());
  }

  @Test(timeOut = 3000)
  public void testPerformance() {
    for (int i = 0; i < 10000; i++) {
      ClassTester performance = TestNGClassTester.createBuilder(OneMethodWithTestGroupSecondWithout.class).addTestGroup(TESTGROUP_UNITTEST).build();
    }
  }

  @Test
  public void testUseOnlyAnnotatedMethods() {
    unitUnderTest = TestNGClassTester.createBuilder(OnlyOneMethodWithTest.class).useOnlyAnnotatedMethods().build();
    assertTrue(unitUnderTest.isValidTestClass());
  }

  @Test
  public void testDoNotIgnoreAbstractClass() {
    unitUnderTest = TestNGClassTester.createBuilder(AbstractTestClass.class).doNotIgnoreAbstractClass().build();
    assertTrue(unitUnderTest.isInvalidTestClass(), unitUnderTest.reportViolation());
  }

  @Test
  public void testAddMultipleTestGroupsAsString() {
    List<String> groupNames = new ArrayList<String>();
    groupNames.add(TESTGROUP_GROUPONE);
    groupNames.add(TESTGROUP_GROUPTWO);
    unitUnderTest = TestNGClassTester.createBuilder(TestMultipleTestGroups.class).setTestGroups(groupNames).build();

    assertTrue(unitUnderTest.isValidTestClass());
  }

  @Test
  public void testTestGroupCount() {
    unitUnderTest = TestNGClassTester.createBuilder(TestGroupTest.class).build();
    Map<String, Integer> testGroups = unitUnderTest.getTestGroupCount();

    assertTrue(testGroups.get("TestGroupTwo") == 3);
    assertTrue(testGroups.size() == 2);
  }
}
