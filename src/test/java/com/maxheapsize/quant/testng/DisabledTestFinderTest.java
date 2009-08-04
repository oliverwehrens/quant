package com.maxheapsize.quant.testng;

import com.maxheapsize.quant.DisabledTestFinder;
import com.maxheapsize.quant.testclasses.DisabledTests;
import com.maxheapsize.quant.testclasses.TwoTestGroups;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

@Test(groups = "unitTest")
public class DisabledTestFinderTest {

  private DisabledTestFinder unitUnderTest;

  @Test
  public void testDisabledTests() {
    unitUnderTest = TestNGDisabledTestFinder.createBuilder(DisabledTests.class).build();
    assertTrue(unitUnderTest.hasDisabledTests());
  }

  @Test
  public void testNoDisabledTest() {
    unitUnderTest = TestNGDisabledTestFinder.createBuilder(TwoTestGroups.class).build();
    assertFalse(unitUnderTest.hasDisabledTests());
  }

  @Test
  public void testGetDisabledTests() {
    unitUnderTest = TestNGDisabledTestFinder.createBuilder(DisabledTests.class).build();
    assertSame(unitUnderTest.getDisabledTests().size(), 1);
  }

  @Test
  public void testGetDisabledTestClassName() {
    unitUnderTest = TestNGDisabledTestFinder.createBuilder(DisabledTests.class).build();
    assertSame(unitUnderTest.getTestClassName(), DisabledTests.class.getName());
  }
}
