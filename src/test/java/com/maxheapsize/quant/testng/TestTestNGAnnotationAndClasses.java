package com.maxheapsize.quant.testng;

import com.maxheapsize.quant.ClassTester;
import com.maxheapsize.quant.infrastructure.AbstractConcordionTestNgTest;
import org.testng.annotations.Test;

@Test(groups = "concordion")
public class TestTestNGAnnotationAndClasses extends AbstractConcordionTestNgTest {

  private ClassTester unitUnderTest;

  public String getResult(String testClass, String testGroup) throws ClassNotFoundException {
    Class<?> clazz = Class.forName("com.maxheapsize.quant.testclasses." + testClass);
    if (testGroup.equals("")) {
      unitUnderTest = TestNGClassTester.createBuilder(clazz).build();
    }
    else {
      unitUnderTest = TestNGClassTester.createBuilder(clazz).addTestGroup(testGroup).build();
    }
    return unitUnderTest.isValidTestClass() ? "valid" : "invalid";
  }
}
