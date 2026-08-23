package com.github.tiagolofi.core;

import java.util.List;

public record Tool(
    String name, 
    String description, 
    List<String> command
) {}
