package com.github.tiagolofi.core;

import java.util.List;

public record JTool(
    String name, 
    String description, 
    List<String> command,
    List<List<String>> pipeline
) {}
