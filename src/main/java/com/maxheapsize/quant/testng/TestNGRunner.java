package com.maxheapsize.quant.testng;

import com.maxheapsize.quant.*;

import java.util.List;

public class TestNGRunner {

  public void run(String sourceDirectory) {

    ClassFinder classFinder = new ClassFinder.Builder(sourceDirectory).build();
    List<Class> classes = classFinder.getClassList();
    for (Class klass : classes) {
      TestClassTester testAnnotationTester = new TestNGTestClassTester.Builder(klass).build();
      if (!testAnnotationTester.allTestMethodsHaveValidTestGroup()) {
        System.out.println("Some (all) methods of " + klass.getName() + " do not have a test group.");
      }
    }
  }
}
