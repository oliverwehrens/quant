package com.maxheapsize.quant.testclasses;

import com.maxheapsize.quant.testng.TestAnnotationTesterTest;
import org.testng.annotations.Test;

public class TestAnnotationOnlyOnMethod {

  @Test(groups = TestAnnotationTesterTest.TESTGROUP_UNITTEST)
  public void testMe() {

  }
}
