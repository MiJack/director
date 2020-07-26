package com.mijack.director.local.demo;

import com.mijack.director.local.LocalDirectorRegisterService;
import com.mijack.director.meta.definition.TaskDefinition;
import com.mijack.director.meta.definition.TaskReference;
import com.mijack.director.meta.definition.WorkflowDefinition;
import com.mijack.director.meta.execution.WorkflowRequest;
import com.mijack.director.meta.param.ParamDef;
import com.mijack.director.meta.strategy.ConsumerStrategy;
import com.mijack.director.meta.strategy.RetryStrategy;
import com.mijack.director.meta.values.ValueHolder;
import com.mijack.director.meta.values.WorkflowReferenceValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanyujie
 */
public class Main {
    public static void main(String[] args) {
        // 注册线程
        LocalDirectorRegisterService localDirectorRegisterService = new LocalDirectorRegisterService();
        localDirectorRegisterService.start();
        TaskDefinition httpTaskDef = buildTaskDefinition();
        localDirectorRegisterService.registerTask(httpTaskDef);

        WorkflowDefinition httpWorkflowDefinition = buildWorkflowDefinition();

        localDirectorRegisterService.registerWorkflowDefinition(httpWorkflowDefinition);

        WorkflowRequest workflowRequest = new WorkflowRequest();
        workflowRequest.setWorkflowDefinitionId(httpWorkflowDefinition.getId());
        workflowRequest.appendParam("url","http://api.github.com");
        workflowRequest.appendParam("method","POST");
        localDirectorRegisterService.submitWorkflowRequest(workflowRequest);
    }

    private static WorkflowDefinition buildWorkflowDefinition() {
        List<ParamDef> paramDefs = new ArrayList<ParamDef>();
        paramDefs.add(new ParamDef("method", String.class));
        paramDefs.add(new ParamDef("url", String.class));
        List<ValueHolder> outParamDefs = new ArrayList<ValueHolder>();
        outParamDefs.add(new WorkflowReferenceValue("http-task", "output","body"));
        outParamDefs.add(new WorkflowReferenceValue("http-task", "output","status"));


        List<ValueHolder> valueHolders = new ArrayList<ValueHolder>();
        valueHolders.add(new WorkflowReferenceValue("workflow", "input","method"));
        valueHolders.add(new WorkflowReferenceValue("workflow", "input","url"));
        TaskReference taskReference = new TaskReference();
        taskReference.setName("http-task");
        taskReference.setTaskReferenceName("http");
        taskReference.setInputParameters(valueHolders);
        List<TaskReference> list = new ArrayList<TaskReference>();
        list.add(taskReference);
        return WorkflowDefinition.builder()
                .setId("http-workflow")
                .setName("do http request")
                .setInputParamDefs(paramDefs)
                .setOutputResultDefs(outParamDefs)
                .setTaskReferences(list)
                .build();
    }

    private static TaskDefinition buildTaskDefinition() {
        List<ParamDef> paramDefs = new ArrayList<ParamDef>();
        paramDefs.add(new ParamDef("method", String.class));
        paramDefs.add(new ParamDef("url", String.class));
        List<ParamDef> outParamDefs = new ArrayList<ParamDef>();
        outParamDefs.add(new ParamDef("body", String.class));
        outParamDefs.add(new ParamDef("status", String.class));
        return TaskDefinition.builder()
                .setId("http")
                .setName("do http request")
                .setInputParamDefs(paramDefs)
                .setOutputResultDefs(outParamDefs)
                .setRetryStrategy(RetryStrategy.noRetry())
                .setConsumerStrategy(ConsumerStrategy.builder().setPullDuration(1000).build())
                .build();
    }

}
