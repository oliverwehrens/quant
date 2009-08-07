package com.maxheapsize.quant;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

@Test(groups = "unitTest")
public class ClassFinderTest {

  private ClassFinder unitUnderTest;

  @Test
  public void testFindClassesWithExcludedPackages() throws IOException {
    unitUnderTest = new ClassFinder.Builder("target/test-classes").addExcludedPackage("testclasses").
        addExcludedPackage("learningtests").build();

    List<Class> classes = unitUnderTest.getClassList();
    Assert.assertSame(classes.size(), 4);
  }

  @Test
  public void testExcludeAllPackages() throws IOException {
    unitUnderTest = new ClassFinder.Builder("target/test-classes").addExcludedPackage("com.maxheapsize").build();

    List<Class> classes = unitUnderTest.getClassList();
    Assert.assertSame(classes.size(), 0);
  }
}
