package com.maxheapsize.quant;

public interface ClassTester {

  /**
   * Reports if a class does not full fills the specification by the builder.
   *
   * @return true if the class violates the specification.
   */
  boolean isInvalidTestClass();

  /**
   * Reports if class full flls the specification by the builder.
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
}
