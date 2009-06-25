package com.maxheapsize.quant;

public interface ClassTester {

  /**
   * Reports if a class fullfills the specification by the builder.
   *
   * @return true if the class violates the specification.
   */
  boolean isInvalidTestClass();

  /**
   * Reports the violations of the specification.
   *
   * @return Message containing information about the violations.
   */
  String reportViolation();
}
