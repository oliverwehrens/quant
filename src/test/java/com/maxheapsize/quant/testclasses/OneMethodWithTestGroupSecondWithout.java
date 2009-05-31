package com.maxheapsize.quant.testclasses;

import com.maxheapsize.quant.testng.TestAnnotationTesterTest;
import org.testng.annotations.Test;

public class OneMethodWithTestGroupSecondWithout {

  @Test(groups = TestAnnotationTesterTest.TESTGROUP_UNITTEST)
  public void testMe() {

  }

  public void testMeToo() {

  }
}