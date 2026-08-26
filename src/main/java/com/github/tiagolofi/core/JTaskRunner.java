package com.github.tiagolofi.core;

public interface JTaskRunner {

    JResponse execute(JTool tool);

    default Integer validate(JTool tool) {
        return tool != null ? 0 : 1;
    }

}
