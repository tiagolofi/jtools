package com.github.tiagolofi.comandos;

import java.nio.file.Path;

import com.github.tiagolofi.core.Tool;
import com.github.tiagolofi.core.ToolsRepository;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(
    name = "tools",
    description = "Caixa de ferramentas para ambiente linux",
    mixinStandardHelpOptions = true,
    version = "1.0.0",
    subcommands = {
        ListarToolsSubcommand.class
    }
)
public class ToolsCommand implements Runnable {

    private final ToolsRepository toolsRepository;

    public ToolsCommand() {
        this(new ToolsRepository(Path.of("tools.toml")));
    }

    public ToolsCommand(ToolsRepository toolsRepository) {
        this.toolsRepository = toolsRepository;
    }

    @Parameters(index = "0", description = "Nome do comando a ser executado", defaultValue = "onde-estou")
    String command;

    @Override
    public void run() {
        Tool tool = toolsRepository.getTool(command);

        ProcessBuilder processBuilder = new ProcessBuilder(tool.command());

        processBuilder.inheritIO();

        try {
            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new ToolsCommand()).execute(args);
        System.exit(exitCode);
    }
    
}
