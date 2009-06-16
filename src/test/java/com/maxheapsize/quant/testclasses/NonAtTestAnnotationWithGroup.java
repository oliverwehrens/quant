package com.maxheapsize.quant.testclasses;

import com.maxheapsize.quant.testng.TestAnnotationTesterTest;
import org.testng.annotations.BeforeMethod;

public class NonAtTestAnnotationWithGroup {

  @BeforeMethod(groups = TestAnnotationTesterTest.TESTGROUP_UNITTEST)
  public void testSetup() {

  }
}
