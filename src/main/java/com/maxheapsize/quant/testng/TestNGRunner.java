package com.maxheapsize.quant.testng;

import com.maxheapsize.quant.ClassFinder;
import com.maxheapsize.quant.ClassTester;

import java.io.IOException;
import java.util.List;

public class TestNGRunner {

  public void run(String sourceDirectory) throws IOException {

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
