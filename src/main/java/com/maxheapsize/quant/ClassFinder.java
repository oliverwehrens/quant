package com.maxheapsize.quant;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;

public class ClassFinder {

  private final static String SOURCE_FILE_SUFFIX = "java";
  private final static boolean RECURSIVE_SEARCH = true;
  private int ZERO_BASED_OFFSET = 1;
  private String testSourcePath;
  private List<String> allFullyQualifiedTestNames = new ArrayList<String>();
  private List<String> excludedPackages = new ArrayList<String>();
  private List<Class> classList = new ArrayList<Class>();

  // Builder

  public static class Builder {
    String testSourcePath;
    List<String> excludedPackages = new ArrayList<String>();

    public Builder(String testSourcePath) {
      super();
      this.testSourcePath = testSourcePath;
    }

    public Builder addExcludedPackage(String packageName) {
      excludedPackages.add(packageName);
      return this;
    }

    public ClassFinder build() {
      return new ClassFinder(testSourcePath, excludedPackages);
    }
  }

  public List<Class> getClassList() {
    return classList;
  }

  // Private Methods

  private ClassFinder(String testSourcePath, List<String> excludedPackages) {
    this.testSourcePath = testSourcePath;
    this.excludedPackages = excludedPackages;
    examineSources();
  }

  private void examineSources() {
    findFullyQualifiedTestClassNames();
    findWantedTestClassed();
  }

  private void findWantedTestClassed() {
    for (String qualifiedTestName : allFullyQualifiedTestNames) {
      try {

        Class klass = Class.forName(qualifiedTestName);
        classList.add(klass);
      }
      catch (ClassNotFoundException e) {
        System.out.println("Could not load: " + qualifiedTestName);
      }
    }
  }

  private void findFullyQualifiedTestClassNames() {
    File testSourceDirectory = new File(testSourcePath);
    if (testSourceDirectory.isDirectory()) {
      Collection sourceFiles = getAllJavaSourceFiles(testSourceDirectory);
      for (Object sourceFile : sourceFiles) {
        File file = (File) sourceFile;
        String classNameFromPath = createClassNameFromPath(testSourcePath, file.getAbsolutePath());
        if (!isInExcludedPackage(classNameFromPath)) {
          allFullyQualifiedTestNames.add(classNameFromPath);
        }
      }
    }
  }

  private boolean isInExcludedPackage(String classNameFromPath) {
    boolean result = false;
    for (String excludedPackage : excludedPackages) {
      if (classNameFromPath.contains(excludedPackage)) {
        return true;
      }
    }
    return result;
  }

  private String createClassNameFromPath(String testSourcePath, String absoluteClassNamePath) {
    File testSourceBaseDirectory = new File(testSourcePath);
    String classFileName = absoluteClassNamePath.substring(testSourceBaseDirectory.getAbsolutePath().length() + ZERO_BASED_OFFSET);
    String classNameWithSuffix = classFileName.replace(File.separator, ".");
    String fqClassName = classNameWithSuffix.substring(0, classNameWithSuffix.length() - SOURCE_FILE_SUFFIX.length() - ZERO_BASED_OFFSET);
    return fqClassName;
  }

  private Collection getAllJavaSourceFiles(File testSourceDirectory) {
    return FileUtils.listFiles(testSourceDirectory, new String[] {SOURCE_FILE_SUFFIX}, RECURSIVE_SEARCH);
  }
}
