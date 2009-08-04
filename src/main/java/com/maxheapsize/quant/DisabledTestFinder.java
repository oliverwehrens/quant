package com.maxheapsize.quant;

import java.lang.reflect.Method;
import java.util.List;

public interface DisabledTestFinder {

  /**
   * Checks whether the Class has methods with disabled Tests.
   *
   * @return true if the class has disabled test methods.
   */
  boolean hasDisabledTests();

  /**
   * Returns a list with the disabled test methods.
   *
   * @return List with the disabled test methods.
   */
  List<Method> getDisabledTests();

  /**
   * Returns the name of the class which was tested.
   *
   * @return Name of the class
   */
  String getTestClassName();
}
