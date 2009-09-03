package com.maxheapsize.quant.testng;

import com.maxheapsize.quant.ClassFinder;
import com.maxheapsize.quant.ClassTester;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class TestNGRunner {

  private static Logger log = Logger.getLogger(TestNGRunner.class);

  public void run(String sourceDirectory) throws IOException {

    ClassFinder classFinder = new ClassFinder.Builder(sourceDirectory).build();
    List<Class> classes = classFinder.getClassList();
    for (Class klass : classes) {
      ClassTester annotationTester = new TestNGClassTester.Builder(klass).build();
      if (annotationTester.isInvalidTestClass()) {
        log.info("Some (all) methods of " + klass.getName() + " do not have a test group.");
      }
    }
  }
}
