package com.maxheapsize.quant.testclasses;

import com.maxheapsize.quant.testng.TestAnnotationTesterTest;
import org.testng.annotations.*;

@Test(groups = TestAnnotationTesterTest.TESTGROUP_UNITTEST)
public class NoGroupOnTestsJustOnClassWithDifferentAnnotations {

  @Test
  public void testMe() {

  }

  @BeforeMethod
  public void testsetUp() {

  }
}
