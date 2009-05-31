package com.maxheapsize.quant;

import com.maxheapsize.quant.testng.TestNGRunner;
import uk.co.flamingpenguin.jewel.cli.*;

public class Cli {

  public interface CliOptions {
    @Option(shortName = "s", longName = "sourceDir")
    String getSourceDirectory();
  }

  public static void main(String[] args) {

    try {
      CliOptions cliOptions = CliFactory.parseArguments(CliOptions.class, args);
      TestNGRunner testNGRunner = new TestNGRunner();
      String sourceDirOption = cliOptions.getSourceDirectory();
      if (sourceDirOption != null) {
        testNGRunner.run(sourceDirOption);
      }
    }
    catch (ArgumentValidationException e) {
      System.out.println("Usage: --sourceDir the top most source directory");
    }
  }
}
