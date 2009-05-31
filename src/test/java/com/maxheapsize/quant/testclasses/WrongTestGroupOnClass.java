package com.maxheapsize.quant.testclasses;

import com.maxheapsize.quant.testng.TestAnnotationTesterTest;
import org.testng.annotations.Test;

@Test(groups = TestAnnotationTesterTest.TESTGROUP_WRONG)
public class WrongTestGroupOnClass {

  @Test
  public void testMe() {

  }
}
