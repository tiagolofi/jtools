package com.github.tiagolofi.comandos;

import java.nio.file.Path;

import com.github.tiagolofi.core.JResponse;
import com.github.tiagolofi.core.JTaskRunner;
import com.github.tiagolofi.core.JTool;
import com.github.tiagolofi.core.JToolRepository;
import com.github.tiagolofi.repository.TomlRepository;
import com.github.tiagolofi.runners.JStoudTaskRunner;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(
    name = "jtools",
    description = "Caixa de ferramentas para ambiente linux",
    mixinStandardHelpOptions = true,
    version = "1.0.0",
    subcommands = {}
)
public class JTools implements Runnable {

    private final JToolRepository jToolRepository = new TomlRepository(Path.of("jtools.toml"));
    private final JTaskRunner taskRunner = new JStoudTaskRunner();

    @Parameters(index = "0", description = "Nome do comando a ser executado", defaultValue = "pom-exists")
    String command;

    @Override
    public void run() {
        JTool tool = jToolRepository.getTool(command);
        JResponse response = taskRunner.execute(tool);
        System.out.println(response);
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new JTools()).execute(args);
        System.exit(exitCode);
    }
    
}
