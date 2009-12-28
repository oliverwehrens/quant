package com.maxheapsize.quant.infrastructure;

import org.concordion.api.ResultSummary;
import org.concordion.internal.ConcordionBuilder;
import org.testng.annotations.Test;

public abstract class AbstractConcordionTestNgTest {

  @Test(groups = {"concordion"})
  public void processSpecification() throws Throwable {
    ResultSummary resultSummary = new ConcordionBuilder().build().process(this);
    resultSummary.print(System.out, this);
    resultSummary.assertIsSatisfied(this);
  }
}
