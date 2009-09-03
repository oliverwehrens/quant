package com.maxheapsize.quant;

import com.maxheapsize.quant.testng.TestNGClassTester;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class Runner {

  private static Logger log = Logger.getLogger(Runner.class);

  private Runner() {
    super();
  }

  public static void main(String[] args) throws ClassNotFoundException, IOException {

    String dir = args[0];

    ClassFinder classFinder = new ClassFinder.Builder(dir).addExcludedPackage("testclasses").build();

    List<Class> classList = classFinder.getClassList();

    for (Class aClass : classList) {

      ClassTester classTester = TestNGClassTester.createBuilder(aClass).addTestGroup("unitTest").build();
      log.info(aClass.getName() + " is a " + classTester.isValidTestClass() + " test class");
    }
  }
}
