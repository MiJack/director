package com.mijack.director.meta.strategy;

import lombok.Builder;
import lombok.Data;

/**
 * @author yuanyujie
 */
@Data
@Builder(setterPrefix = "set")
public class ConsumerStrategy {
    private long pullDuration;
}
