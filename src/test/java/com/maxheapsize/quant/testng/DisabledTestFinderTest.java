package com.maxheapsize.quant.testng;

import com.maxheapsize.quant.DisabledTestFinder;
import com.maxheapsize.quant.testclasses.*;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

@Test(groups = "unitTest")
public class DisabledTestFinderTest {

  private DisabledTestFinder unitUnderTest;

  @Test
  public void testDisabledTests() {
    unitUnderTest = new TestNGDisabledTestFinder.Builder(DisabledTests.class).build();
    assertTrue(unitUnderTest.hasDisabledTests());
  }

  @Test
  public void testNoDisabledTest() {
    unitUnderTest = new TestNGDisabledTestFinder.Builder(TwoTestGroups.class).build();
    assertFalse(unitUnderTest.hasDisabledTests());
  }

  @Test
  public void testGetDisabledTests() {
    unitUnderTest = new TestNGDisabledTestFinder.Builder(DisabledTests.class).build();
    assertSame(unitUnderTest.getDisabledTests().size(), 1);
  }
}
