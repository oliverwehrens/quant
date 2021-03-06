package com.maxheapsize.quant.testng;

import com.maxheapsize.quant.ClassTester;
import com.maxheapsize.quant.infrastructure.AbstractConcordionTestNgTest;
import org.testng.annotations.Test;

public class TestTestNGAnnotationAndClasses extends AbstractConcordionTestNgTest {

  public String getResult(String testClass, String testGroup) throws ClassNotFoundException {
    ClassTester unitUnderTest;

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
