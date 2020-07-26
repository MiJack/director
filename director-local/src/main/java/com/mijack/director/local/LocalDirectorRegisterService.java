package com.mijack.director.local;

import com.mijack.director.core.execute.WorkflowRequestExecutor;
import com.mijack.director.core.register.TaskRegister;
import com.mijack.director.core.register.WorkflowRegister;
import com.mijack.director.meta.definition.TaskDefinition;
import com.mijack.director.meta.definition.TaskReference;
import com.mijack.director.meta.definition.WorkflowDefinition;
import com.mijack.director.meta.execution.Params;
import com.mijack.director.meta.execution.WorkflowRequest;
import com.mijack.director.meta.param.ParamDef;
import com.mijack.director.meta.strategy.ConsumerStrategy;
import com.mijack.director.meta.strategy.RetryStrategy;
import com.mijack.director.meta.values.ValueHolder;
import com.mijack.director.meta.values.WorkflowReferenceValue;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author yuanyujie
 */
public class LocalDirectorRegisterService extends Thread implements WorkflowRegister, TaskRegister, WorkflowRequestExecutor {
    Logger logger = Logger.getLogger(LocalDirectorRegisterService.class.getSimpleName());

    public LocalDirectorRegisterService() {
        setName("LocalDirectorDirectorRegisterService-Thread");
    }

    private ConcurrentHashMap<String, TaskDefinition> taskDefinitionMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, WorkflowDefinition> workflowDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public boolean registerTask(TaskDefinition taskDefinition) {
        if (taskDefinition.getId() == null) {
            return false;
        }
        if (taskDefinition.getConsumerStrategy() == null) {
            taskDefinition.setConsumerStrategy(ConsumerStrategy.builder().setPullDuration(1000).build());
        }
        if (taskDefinition.getRetryStrategy() == null) {
            taskDefinition.setRetryStrategy(RetryStrategy.noRetry());
        }
        if (taskDefinition.getInputParamDefs() == null) {
            taskDefinition.setInputParamDefs(Collections.emptyList());
        }
        if (taskDefinition.getOutputResultDefs() == null) {
            taskDefinition.setOutputResultDefs(Collections.emptyList());
        }
        if (taskDefinitionMap.putIfAbsent(taskDefinition.getId(), taskDefinition) == null) {
            logger.info("registerTask: " + taskDefinition.getId());
            return true;
        }
        return false;
    }

    @Override
    public boolean unregisterTask(String taskDefinitionId) {
        return taskDefinitionMap.remove(taskDefinitionId) != null;
    }


    @Override
    public boolean registerWorkflowDefinition(WorkflowDefinition workflowDefinition) {
        for (TaskReference taskReference : workflowDefinition.getTaskReferences()) {
            String taskReferenceName = taskReference.getTaskReferenceName();
            if (!taskDefinitionMap.containsKey(taskReferenceName)) {
                logger.warning("no task found for " + taskReferenceName);
                return false;
            }
        }
        Map<String, TaskReference> executeTaskNames = workflowDefinition.getTaskReferences().stream()
                .collect(Collectors.toMap(TaskReference::getTaskReferenceName, new Function<TaskReference, TaskReference>() {
                    @Override
                    public TaskReference apply(TaskReference taskReference) {
                        return taskReference;
                    }
                }));
        List<ValueHolder> valueHolders = new ArrayList<>();
        valueHolders.addAll(workflowDefinition.getOutputResultDefs());

        workflowDefinition.getTaskReferences().stream().flatMap(tr -> tr.getInputParameters().stream())
                .forEach(valueHolders::add);


        for (ValueHolder vh : valueHolders) {
            if (vh instanceof WorkflowReferenceValue) {
                WorkflowReferenceValue v = (WorkflowReferenceValue) vh;
                String f = v.getSource();
                if (!"workflow".equalsIgnoreCase(f)) {
                    if (!executeTaskNames.containsKey(f)) {
                        logger.info("no task definition in workflow: taskName = "+f);
                        return false;
                    }
                }
            }
        }
        boolean result = workflowDefinitionMap.putIfAbsent(workflowDefinition.getId(), workflowDefinition) == null;
        if (result) {
            logger.info("workflowDefinition: " + workflowDefinition.getId());
            return true;
        }
        return false;
    }

    @Override
    public boolean unregisterWorkflow(String workflowDefinitionId) {
        return workflowDefinitionMap.remove(workflowDefinitionId) != null;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            logger.info("LocalDirectorRegisterService loop");
            Thread.sleep(1000);
        }
    }

    @Override
    public boolean submitWorkflowRequest(WorkflowRequest workflowRequest) {
        WorkflowDefinition workflowDefinition = workflowDefinitionMap.get(workflowRequest.getWorkflowDefinitionId());
        if (workflowDefinition==null){
            return false;
        }
        List<ParamDef> inputParamDefs = workflowDefinition.getInputParamDefs();
        for (ParamDef paramDef : inputParamDefs) {
            String paramDefName = paramDef.getName();
            Params params = workflowRequest.getParamsMap().get(paramDefName);
            if (params == null) {
                return false;
            }

            // todo implement accept
            throw new UnsupportedOperationException();
        }
//        workflowRequest.getParamsMap()
        // todo implement submitWorkflowRequest
        throw new UnsupportedOperationException();
    }
}
