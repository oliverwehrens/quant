package com.maxheapsize.quant.testng;

import com.maxheapsize.quant.*;

import java.util.List;

public class TestNGRunner {

  public void run(String sourceDirectory) {

    ClassFinder classFinder = new ClassFinder.Builder(sourceDirectory).build();
    List<Class> classes = classFinder.getClassList();
    for (Class klass : classes) {
      ClassTester annotationTester = new TestNGClassTester.Builder(klass).build();
      if (annotationTester.isInvalidTestClass()) {
        System.out.println("Some (all) methods of " + klass.getName() + " do not have a test group.");
      }
    }
  }
}
