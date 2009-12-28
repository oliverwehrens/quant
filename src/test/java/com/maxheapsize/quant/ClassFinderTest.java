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
    unitUnderTest = ClassFinder.createBuilder("target/test-classes").
        addExcludedPackage("testclasses").
        addExcludedPackage("testgroups").
        addExcludedPackage("learningtests").build();

    List<Class> classes = unitUnderTest.getClassList();
    Assert.assertSame(classes.size(), 7);
  }

  @Test
  public void testExcludeAllPackages() throws IOException {
    unitUnderTest = ClassFinder.createBuilder("target/test-classes").addExcludedPackage("com.maxheapsize").build();

    List<Class> classes = unitUnderTest.getClassList();
    Assert.assertSame(classes.size(), 0);
  }

  @Test
  public void testClassInTestList() throws IOException {
    unitUnderTest = ClassFinder.createBuilder("target/test-classes").build();
    List<Class> classes = unitUnderTest.getClassList();
    boolean foundThisTestFinderClass = false;
    for (Class aClass : classes) {
      if (aClass.getName().equals(this.getClass().getName())) {
        foundThisTestFinderClass = true;
      }
    }

    Assert.assertTrue(foundThisTestFinderClass);
  }
}
