package com.github.tiagolofi.comandos;

import com.github.tiagolofi.core.JCommand;
import com.github.tiagolofi.core.JTool;

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
public class JTools extends JCommand {

    @Parameters(index = "0", description = "Nome do comando a ser executado", defaultValue = "pwd")
    String command;

    @Override
    public void run() {
        System.out.println("Jtools v1.0.0\nCaixa de ferramentas para ambiente linux\nComandos disponíveis:");
        getJToolsRepository().getAllTools().forEach(tool -> {
            System.out.println("\t`" + tool.name() + "`: " + tool.description());
        });

        JTool tool = getJToolsRepository().getTool(command);

        execute(tool);
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new JTools()).execute(args);
        System.exit(exitCode);
    }
    
}
