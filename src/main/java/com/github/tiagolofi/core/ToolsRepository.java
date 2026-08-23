package com.github.tiagolofi.core;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.tomlj.Toml;
import org.tomlj.TomlParseResult;

public class ToolsRepository {
    
    // arquivo TOML que contém as informações dos comandos
    private final TomlParseResult tomlFile;

    // chaves para acessar os valores no arquivo TOML
    private static final String DESCRIPTION_KEY = "desc";
    private static final String COMMAND_KEY = "command";

    public ToolsRepository(Path file) {

        if (!file.toFile().exists()) {
            throw new RuntimeException("Arquivo de configuração não encontrado");
        }

        try {
            this.tomlFile = Toml.parse(file);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo de configuração", e);
        }   
    }

    public List<Tool> getAllTools() {
        return tomlFile.toMap().keySet().stream()
            .map(this::getTool)
            .toList();
    }

    public Tool getTool(String commandName) {
        try {
            String description = tomlFile.getTable(commandName)
                .getString(DESCRIPTION_KEY);

            List<String> commandList = tomlFile.getTable(commandName)
                .getArray(COMMAND_KEY)
                .toList()
                .stream()
                .map(String::valueOf)
                .toList();

            return new Tool(commandName, description, commandList);
        } catch (Exception e) {
            return null;
        }
    }

}
