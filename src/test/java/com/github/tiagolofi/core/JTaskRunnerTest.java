package com.github.tiagolofi.core;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.github.tiagolofi.runners.JInteractiveTaskRunner;
import com.github.tiagolofi.runners.JSimpleTaskRunner;
import com.github.tiagolofi.runners.JStoudTaskRunner;

public class JTaskRunnerTest {
    
    @ParameterizedTest
    @MethodSource("options")
    public void testTaskRunner(JTaskRunner runner, JTool tool, Integer expectedExitCode, String expectOutput) {
        Assertions.assertDoesNotThrow(() -> {
            JResponse response = runner.execute(tool);

            Assertions.assertNotNull(response);
            Assertions.assertEquals(expectedExitCode, response.exitCode());
            Assertions.assertEquals(expectOutput, response.output());
        });
    }

    private static Stream<Arguments> options() {
        return Stream.of(
            Arguments.of(new JSimpleTaskRunner(), new JTool("test", "", List.of("pwd"), null), 0, null),
            Arguments.of(new JInteractiveTaskRunner(), new JTool("test", "", List.of("pwd"), null), 0, null),
            Arguments.of(new JStoudTaskRunner(), new JTool("test", "", List.of("tail", "pom.xml", "-n", "1"), null), 0, "</project>")
        );
    }

}
