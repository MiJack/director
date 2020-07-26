package com.mijack.director.meta.execution;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author yuanyujie
 */
@Data
public class WorkflowRequest {
    private String workflowDefinitionId;
    private Map<String, Params> paramsMap = new HashMap<>();

    public void appendParam(String key, String value) {
        paramsMap.computeIfAbsent(key, new Function<String, Params>() {
            @Override
            public Params apply(String s) {
                return new Params();
            }
        }).add(value);
    }
}
