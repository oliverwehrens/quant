package com.maxheapsize.quant;

import java.lang.reflect.Method;
import java.util.List;

public interface DisabledTestFinder {

  /**
   * Checks whether the Class has methods with disabled Tests.
   * @return
   */
  public boolean hasDisabledTests();

  public List<Method> getDisabledTests();
}
