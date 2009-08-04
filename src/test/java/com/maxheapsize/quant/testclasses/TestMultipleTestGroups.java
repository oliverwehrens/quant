package com.maxheapsize.quant.testclasses;

import com.maxheapsize.quant.testng.TestAnnotationTesterTest;
import org.testng.annotations.Test;

public class TestMultipleTestGroups {

  @Test(groups = TestAnnotationTesterTest.TESTGROUP_GROUPONE)
  public void testGroupOne() {

  }

  @Test(groups = TestAnnotationTesterTest.TESTGROUP_GROUPTWO)
  public void testGroupTwo() {

  }
}
