package com.mijack.director.meta.definition;

import com.mijack.director.meta.param.ParamDef;
import com.mijack.director.meta.values.ValueHolder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author yuanyujie
 */
@Data
@Builder(setterPrefix = "set")
public class WorkflowDefinition {
    /**
     * The id of workflow definition
     */
    private String id;
    /**
     * The name of workflow definition
     */
    private String name;
    /**
     * The task reference
     */
    private List<TaskReference> taskReferences;
    /**
     * The definition for the input param
     */
    private List<ParamDef> inputParamDefs;
    /**
     * The definition for the output result
     */
    private List<ValueHolder> outputResultDefs;
}
