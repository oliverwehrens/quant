package com.maxheapsize.quant.testng;

import org.testng.annotations.Test;

@Test(groups = "unitTest")
public class TestNGRunnerTest {

  @Test
  public void testTestNGRunner() {
    TestNGRunner testNGRunner = new TestNGRunner();
    testNGRunner.run("src/test/java");
  }
}
