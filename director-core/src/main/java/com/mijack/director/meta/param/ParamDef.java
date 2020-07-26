package com.mijack.director.meta.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;

/**
 * @author yuanyujie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamDef {
    private String name;
    private Type type;
}
