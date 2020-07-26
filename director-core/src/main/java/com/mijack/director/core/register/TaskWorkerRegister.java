
package com.mijack.director.core.register;

/**
 * @author yuanyujie
 */
public interface TaskWorkerRegister {
    /**
     *
     * @param taskId
     * @param workerId
     * @return
     */
    boolean registerTaskWorker(String taskId,String workerId);

    /**
     *
     * @param taskId
     * @param workerId
     * @return
     */
    boolean unregisterTaskWorker(String taskId,String workerId);
}
