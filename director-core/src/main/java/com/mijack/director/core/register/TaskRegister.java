
package com.mijack.director.core.register;

import com.mijack.director.meta.definition.TaskDefinition;

/**
 * @author yuanyujie
 */
public interface TaskRegister {
    /**
     * 注册 TaskDefinition
     *
     * @param taskDefinition
     * @return
     */
    boolean registerTask(TaskDefinition taskDefinition);

    /**
     * 注销 TaskDefinition
     *
     * @param taskDefinitionId
     * @return
     */
    boolean unregisterTask(String taskDefinitionId);
}
