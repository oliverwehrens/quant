package com.maxheapsize.quant;

import com.maxheapsize.quant.testng.TestNGClassTester;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Runner {

  private static Logger log = Logger.getLogger(Runner.class);

  private Runner() {
    super();
  }

  public static void main(String[] args) throws ClassNotFoundException, IOException {
    String dir = args[0];
    ClassFinder classFinder = new ClassFinder.Builder(dir).build();

    List<Class> classList = classFinder.getClassList();
    for (Class aClass : classList) {
      ClassTester classTester = TestNGClassTester.createBuilder(aClass).addTestGroup("unitTest").build();
      log.info(aClass.getName() + " is a " + classTester.isValidTestClass() + " test class");
      Map<String, Integer> groupCount = classTester.getTestGroupCount();
      for (String testGroup : groupCount.keySet()) {
        log.info("Has " + groupCount.get(testGroup) + " test(s) in test group" + testGroup);
      }
    }
  }
}
