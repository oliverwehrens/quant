package com.maxheapsize.quant;

import java.lang.reflect.Method;
import java.util.List;

public interface DisabledTestFinder {

  public boolean hasDisabledTests();

  public List<Method> getDisabledTests();
}
