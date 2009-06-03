
- Assumptions

TestNGClassTester:

  * Each public void method in a test class is a potential test method, that means each public void method
    in a test class without an @Test annotation will report as invalid (as long as the class itself is not
    annotated with @Test)
  * Abstract test classes are not evaluated (unles you specify .doNotIgnoreAbstractClass() in the builder)
  * If a test group is specified in the builder via .addTestGroup("TestGroup") then all methods with relavent
    TestNG annotations without a group will be reported invalid

TestClassFinder:

  * All tests are under the given source tree
  * If you want to exclude a certain package simply use .addExcludedPackage("doNotInclude")
    This would load all classes except those which do have the specified String in the packagename
    e.g. com.maxheapsize.doNotInclude.me would be exclude but also com.maxheapsize.PleasedoNotIncludeThisPackage.me

 DisabledTestFinder:

   * The DisabledTestFinder will find all test methods which are disabled with @Test(enabled = false)