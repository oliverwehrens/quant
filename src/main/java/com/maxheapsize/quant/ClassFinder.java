package com.maxheapsize.quant;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ClassFinder {

  private static final String FILE_SUFFIX = "class";
  private static final boolean RECURSIVE_SEARCH = true;
  private static final int ZERO_BASED_OFFSET = 1;
  private String testClassPath;
  private List<String> allFullyQualifiedTestNames = new ArrayList<String>();
  private List<String> excludedPackages = new ArrayList<String>();
  private List<Class> classList = new ArrayList<Class>();

  // Builder

  public static class Builder {

    private String testClassPath;
    private List<String> excludedPackages = new ArrayList<String>();

    /**
     * Constructs a ClassFinder which will try to load all classes under the given testSourcePath.
     *
     * @param testClassPath
     */
    public Builder(String testClassPath) {
      super();
      this.testClassPath = testClassPath;
    }

    /**
     * Classes with the specified package name will not be loaded. 'notInclude' will exclude all classes with the String
     * 'notInclude' in the fully qualified class name.
     *
     * @param packageName
     *
     * @return Builder
     */
    public final Builder addExcludedPackage(String packageName) {
      excludedPackages.add(packageName);
      return this;
    }

    /**
     * Return a ClassFinder with the given builder specifications.
     *
     * @return ClassFinder
     */
    public final ClassFinder build() throws IOException {
      return new ClassFinder(testClassPath, excludedPackages);
    }
  }

 public static Builder createBuilder(String testClassPath) {
     return new Builder(testClassPath);
 }

  /**
   * Returns the list of classes which are loaded according to the specifications.
   *
   * @return List of classes
   */
  public List<Class> getClassList() {
    return classList;
  }

  // Private Methods

  private ClassFinder(String testClassPath, List<String> excludedPackages) throws IOException {
    this.testClassPath = testClassPath;
    this.excludedPackages = excludedPackages;
    examineSources();
  }

  private void examineSources() throws IOException {
    findFullyQualifiedTestClassNames();
    findWantedTestClassed();
  }

  private void findWantedTestClassed() throws IOException {
    CustomClassLoader customClassLoader = new CustomClassLoader(testClassPath);
    for (String qualifiedTestName : allFullyQualifiedTestNames) {
      Class klass = customClassLoader.findClass(qualifiedTestName);
      classList.add(klass);
    }
  }

  private void findFullyQualifiedTestClassNames() {
    File testSourceDirectory = new File(testClassPath);
    if (testSourceDirectory.isDirectory()) {
      Collection sourceFiles = getAllJavaSourceFiles(testSourceDirectory);
      for (Object sourceFile : sourceFiles) {
        File file = (File) sourceFile;
        String classNameFromPath = createClassNameFromPath(testClassPath, file.getAbsolutePath());
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

  private String createClassNameFromPath(String testClassPath, String absoluteClassNamePath) {
    File testClassBaseDirectory = new File(testClassPath);
    String classFileName = absoluteClassNamePath.substring(testClassBaseDirectory.getAbsolutePath().length() + ZERO_BASED_OFFSET);
    String classNameWithSuffix = classFileName.replace(File.separator, ".");
    return classNameWithSuffix.substring(0, classNameWithSuffix.length() - FILE_SUFFIX.length() - ZERO_BASED_OFFSET);
  }

  private Collection getAllJavaSourceFiles(File testClassDirectory) {
    return FileUtils.listFiles(testClassDirectory, new String[] {FILE_SUFFIX}, RECURSIVE_SEARCH);
  }
}
