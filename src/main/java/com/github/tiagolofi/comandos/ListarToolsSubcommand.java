package com.github.tiagolofi.comandos;

import java.nio.file.Path;

import com.github.tiagolofi.core.ToolsRepository;

import picocli.CommandLine.Command;

@Command(
    name = "listar",
    description = "Lista todos os comandos disponíveis",
    mixinStandardHelpOptions = true,
    version = "1.0.0"
)
public class ListarToolsSubcommand implements Runnable{
    
    private final ToolsRepository toolsRepository;

    public ListarToolsSubcommand() {
        this(new ToolsRepository(Path.of("tools.toml")));
    }

    public ListarToolsSubcommand(ToolsRepository toolsRepository) {
        this.toolsRepository = toolsRepository;
    }

    public void run() {
        System.out.println("Comandos disponíveis:");
        toolsRepository.getAllTools().forEach(tool -> System.out.println("- " + tool.name()));
    }

}
