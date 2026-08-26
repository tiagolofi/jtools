package com.github.tiagolofi.runners;

import java.util.ArrayList;
import java.util.List;

import com.github.tiagolofi.core.JResponse;
import com.github.tiagolofi.core.JTaskRunner;
import com.github.tiagolofi.core.JTool;

public class JStoudTaskRunner implements JTaskRunner {

    @Override
    public JResponse execute(JTool tool) {
        validate(tool);
        if (!tool.command().isEmpty()) {
            ProcessBuilder processBuilder = new ProcessBuilder(tool.command());
            try {
                Process process = processBuilder.start();
                String output = new String(process.getInputStream().readAllBytes()).trim();
                int exitCode = process.waitFor();
                return new JResponse(output, exitCode);
            } catch (Exception e) {
                e.printStackTrace();
                return new JResponse(null, 1);
            }
        }

        List<ProcessBuilder> processBuilders = new ArrayList<>();
        for (List<String> process : tool.pipeline()) {
            processBuilders.add(new ProcessBuilder(process));
        }

        try {
            List<Process> processes = ProcessBuilder.startPipeline(processBuilders);
            Process last = processes.getLast();
            String outputLast = new String(processes.getLast().getInputStream().readAllBytes()).trim();
            return new JResponse(outputLast, last.waitFor());
        } catch (Exception e) {
            e.printStackTrace();
            return new JResponse(null, 1);
        }
    }
}
