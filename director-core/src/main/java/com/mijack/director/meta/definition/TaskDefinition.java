package com.mijack.director.meta.definition;

import com.mijack.director.meta.param.ParamDef;
import com.mijack.director.meta.strategy.ConsumerStrategy;
import com.mijack.director.meta.strategy.RetryStrategy;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author yuanyujie
 */
@Data
@Builder(setterPrefix = "set")
public class TaskDefinition {
    /**
     * The id of task definition
     */
    private String id;
    /**
     * The name of task definition
     */
    private String name;

    /**
     * The definition for the input param
     */
    private List<ParamDef> inputParamDefs;
    /**
     * The definition for the output result
     */
    private List<ParamDef> outputResultDefs;
    /**
     * The strategy for retry.
     */
    private RetryStrategy retryStrategy;
    /**
     * The strategy for consumer.
     */
    private ConsumerStrategy consumerStrategy;
}
