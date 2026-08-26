package com.github.tiagolofi.core;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.github.tiagolofi.repository.TomlRepository;

public class JToolRepositoryTest {

    @ParameterizedTest
    @MethodSource("options")
    public void testToolRepository(String toolName, boolean isNull) {
        Path file = Path.of("src/test/resources/tools.toml");

        JToolRepository toolsRepository = new TomlRepository(file);

        JTool tool = toolsRepository.getTool(toolName);
        if (isNull) {
            Assertions.assertNotNull(tool);
        } else {
            Assertions.assertNull(tool);
        }
    }

    private static Stream<Arguments> options() {
        return Stream.of(
            Arguments.of("pwd", true),
            Arguments.of("invalid", false)
        );
    }

    @Test
    public void erroNoArquivoConfiguracao() {
        Path file = Path.of("arquivo-que-nao-existe.toml");

        RuntimeException exception =
            assertThrows(
                RuntimeException.class,
                () -> new TomlRepository(file)
            );

        assertTrue(
            exception
                .getMessage()
                .contains("Arquivo de configuração não encontrado")
        );
    }

}
