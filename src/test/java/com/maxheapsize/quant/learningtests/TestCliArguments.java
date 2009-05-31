package com.maxheapsize.quant.learningtests;

import org.testng.Assert;
import org.testng.annotations.Test;
import uk.co.flamingpenguin.jewel.cli.*;

@Test(groups = "learningTest")
public class TestCliArguments {

  public interface CliOptions {
    @Option(shortName = "s", longName = "sourceDir")
    String getSourceDirectory();

    @Option(shortName = "x")
    String getExcluded();
  }

  @Test
  public void testCli() {
    String args[] = {"--sourceDir", "test", "-x", "test2"};

    try {
      CliOptions cliOptions = CliFactory.parseArguments(CliOptions.class, args);
      Assert.assertEquals(cliOptions.getSourceDirectory(), "test");
      Assert.assertEquals(cliOptions.getExcluded(), "test2");
    }
    catch (ArgumentValidationException e) {
      Assert.assertFalse(true);
    }
  }

  @Test
  public void testDoesNotProvideOption() {
    String args[] = {"-x", "test2"};
    try {
      CliFactory.parseArguments(CliOptions.class, args);
    }
    catch (ArgumentValidationException e) {
      Assert.assertTrue(true);
    }
  }
}
