package com.maxheapsize.quant;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@Test(groups = "unitTest")
public class ClassFinderTest {

  private ClassFinder unitUnderTest;

  @Test
  public void testFindClass() {
    unitUnderTest = new ClassFinder.Builder("src/test/java").build();

    List<Class> classes = unitUnderTest.getClassList();
    Assert.assertSame(classes.size(), 13);
  }

  @Test
  public void testFindClassesWithExcludedPackages() {
    unitUnderTest = new ClassFinder.Builder("src/test/java").addExcludedPackage("testclasses").addExcludedPackage("learningtests").build();

    List<Class> classes = unitUnderTest.getClassList();
    Assert.assertSame(classes.size(), 4);
  }

  @Test
  public void testExcludeAllPackages() {
    unitUnderTest = new ClassFinder.Builder("src/test/java").addExcludedPackage("com.maxheapsize").build();

    List<Class> classes = unitUnderTest.getClassList();
    Assert.assertSame(classes.size(), 0);
  }
}
