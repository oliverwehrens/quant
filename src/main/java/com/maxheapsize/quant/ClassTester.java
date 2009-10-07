package com.maxheapsize.quant;

import java.util.Map;

public interface ClassTester {

  /**
   * Reports if a class does not full fills the specification by the builder.
   *
   * @return true if the class violates the specification.
   */
  boolean isInvalidTestClass();

  /**
   * Reports if class full fills the specification by the builder.
   *
   * @return true if the class full fills the specification
   */
  boolean isValidTestClass();

  /**
   * Reports the violations of the specification.
   *
   * @return Message containing information about the violations.
   */
  String reportViolation();

  /**
   * Returns a Map containing TestGroup Names as Key and the number of test in this group as Integer.
   *
   * @return
   */
  Map<String, Integer> getTestGroupCount();
}
