package com.mijack.director.core.execute;

import com.mijack.director.meta.definition.WorkflowDefinition;
import com.mijack.director.meta.execution.WorkflowRequest;

/**
 * @author yuanyujie
 */
public interface WorkflowRequestExecutor {
    /**
     * 注册workflow
     *
     * @param workflowDefinition
     * @return
     */
    boolean submitWorkflowRequest(WorkflowRequest workflowDefinition);
}
