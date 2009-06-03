package com.maxheapsize.quant.testclasses;

import com.maxheapsize.quant.testng.TestAnnotationTesterTest;
import org.testng.annotations.*;

public class SetupMethodWithoutTestGroup {

  @BeforeMethod
  public void testSetUp() {

  }

  @Test(groups = TestAnnotationTesterTest.TESTGROUP_UNITTEST)
  public void testMe() {

  }
}
