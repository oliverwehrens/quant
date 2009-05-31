package com.maxheapsize.quant.testclasses;

import com.maxheapsize.quant.testng.TestAnnotationTesterTest;
import org.testng.annotations.Test;

@Test(groups = TestAnnotationTesterTest.TESTGROUP_UNITTEST)
public class AnnotationOnlyOnClassWithTestGroup {

  public void testMe() {

  }
}
