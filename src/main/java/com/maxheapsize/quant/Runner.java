package com.maxheapsize.quant;

import com.maxheapsize.quant.testng.TestNGClassTester;

import java.io.IOException;
import java.util.List;

public class Runner {

  public static void main(String[] args) throws ClassNotFoundException, IOException {

    String dir = args[0];

    ClassFinder classFinder = new ClassFinder.Builder(dir).addExcludedPackage("testclasses").build();

    List<Class> classList = classFinder.getClassList();

    for (Class aClass : classList) {

      ClassTester classTester = TestNGClassTester.createBuilder(aClass).addTestGroup("unitTest").build();
      System.out.println(aClass.getName() + " is a " + classTester.isValidTestClass() + " test class");
    }
  }
}
