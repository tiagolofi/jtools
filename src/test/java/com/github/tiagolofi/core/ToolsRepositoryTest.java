package com.github.tiagolofi.core;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ToolsRepositoryTest {
    
    @Test
    public void testGetTool() {
        Path file = Path.of("src/test/resources/tools.toml");

        ToolsRepository toolsRepository = new ToolsRepository(file);

        Tool tool = toolsRepository.getTool("onde-estou");
        Assertions.assertNotNull(tool);
    }
        
    @Test
    public void testGetAllTools() {
        Path file = Path.of("src/test/resources/tools.toml");

        ToolsRepository toolsRepository = new ToolsRepository(file);

        List<Tool> tools = toolsRepository.getAllTools();
        Assertions.assertFalse(tools.isEmpty());
    }

    @Test
    public void testGetToolNotFound() {
        Path file = Path.of("src/test/resources/tools.toml");

        ToolsRepository toolsRepository = new ToolsRepository(file);

        Tool tool = toolsRepository.getTool("non-existent-tool");
        Assertions.assertNull(tool);
    }

    @Test
    public void erroNoArquivoConfiguracao() {
        Path file = Path.of("arquivo-que-nao-existe.toml");

        RuntimeException exception =
            assertThrows(
                RuntimeException.class,
                () -> new ToolsRepository(file)
            );

        assertTrue(
            exception
                .getMessage()
                .contains("Arquivo de configuração não encontrado")
        );
    }

}
