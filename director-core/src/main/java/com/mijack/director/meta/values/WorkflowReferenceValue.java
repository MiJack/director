package com.mijack.director.meta.values;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuanyujie
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowReferenceValue extends ValueHolder {

    /**
     * can be either "workflow" or any of the task reference name
     */
    private String source;
    /**
     * refers to either the input or output of the source
     */
    private String scope;
    /**
     * JSON path expression to extract JSON fragment from source's input/output
     */
    private String path;
}
