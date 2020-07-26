package com.mijack.director.meta.execution;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanyujie
 */
public class Params {
   private String name;
   private List<String> paramValues=new ArrayList<>();
    public void add(String value) {
        paramValues.add(value);
    }
}
