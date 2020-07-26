package com.mijack.director.meta.definition;

import com.mijack.director.meta.values.ValueHolder;
import lombok.Data;

import java.util.List;

/**
 * @author yuanyujie
 */
@Data
public class TaskReference {
    private String name;
    private String taskReferenceName;
    private List<ValueHolder> inputParameters;
}
