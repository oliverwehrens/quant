package com.maxheapsize.quant.testclasses;

import com.maxheapsize.quant.testng.TestAnnotationTesterTest;
import org.testng.annotations.Test;

public class WrongTestGroupOnMethod {

  @Test(groups = TestAnnotationTesterTest.TESTGROUP_WRONG)
  public void testMe() {

  }
}
