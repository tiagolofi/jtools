package com.github.tiagolofi.repository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.tomlj.Toml;
import org.tomlj.TomlArray;
import org.tomlj.TomlParseResult;

import com.github.tiagolofi.core.JTool;
import com.github.tiagolofi.core.JToolRepository;

public class TomlRepository implements JToolRepository {
    
    // arquivo TOML que contém as informações dos comandos
    private final TomlParseResult tomlFile;

    // chaves para acessar os valores no arquivo TOML
    private static final String DESCRIPTION_KEY = "desc";
    private static final String COMMAND_KEY = "command";
    private static final String PIPELINE_KEY = "pipeline";

    public TomlRepository(Path file) {
        if (!file.toFile().exists()) {
            throw new RuntimeException("Arquivo de configuração não encontrado");
        }

        try {
            this.tomlFile = Toml.parse(file);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo de configuração", e);
        }   
    }

    @Override
    public JTool getTool(String commandName) {
        try {
            String description = tomlFile.getTable(commandName)
                .getString(DESCRIPTION_KEY);

            List<String> commandList = tomlFile.getTable(commandName)
                .getArray(COMMAND_KEY)
                .toList()
                .stream()
                .map(String::valueOf)
                .toList();

            List<List<String>> pipelineList = tomlFile.getTable(commandName)
                .getArray(PIPELINE_KEY)
                .toList()
                .stream()
                .map(item -> {
                    if (item instanceof TomlArray array){

                        List<String> arrayToString = array.toList()
                            .stream()
                            .map(String::valueOf)
                            .toList();

                        return arrayToString;
                    }
                    return null;
                })
                .toList();

            return new JTool(commandName, description, commandList, pipelineList);
        } catch (Exception e) {
            return null;
        }
    }

}
