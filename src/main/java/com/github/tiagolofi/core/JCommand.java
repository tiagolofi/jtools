package com.github.tiagolofi.core;

import java.nio.file.Path;

public abstract class JCommand implements Runnable {
    
    private final JToolsRepository toolsRepository;

    private static final String FILENAME = "jtools.toml";

    public JCommand() {
        this(new JToolsRepository(Path.of(FILENAME)));
    }

    public JCommand(JToolsRepository toolsRepository) {
        this.toolsRepository = toolsRepository;
    }

    public JToolsRepository getJToolsRepository() {
        return toolsRepository;
    }

    public Integer execute(JTool tool) {
        if (tool == null) {
            System.err.println("Comando não encontrado, nada a executar.");
            return 1;
        }
        ProcessBuilder processBuilder = new ProcessBuilder(tool.command());
        processBuilder.inheritIO();
        try {
            Process process = processBuilder.start();
            return process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

}
