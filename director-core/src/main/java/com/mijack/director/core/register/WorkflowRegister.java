package com.mijack.director.core.register;

import com.mijack.director.meta.definition.WorkflowDefinition;

/**
 * @author yuanyujie
 */
public interface WorkflowRegister {
    /**
     * 注册workflow
     *
     * @param workflowDefinition
     * @return
     */
    boolean registerWorkflowDefinition(WorkflowDefinition workflowDefinition);
    /**
     * 注销workflow
     *
     * @param workflowDefinitionId
     * @return
     */
    boolean unregisterWorkflow(String workflowDefinitionId);
}
