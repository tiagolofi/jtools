package com.github.tiagolofi.runners;

import java.util.ArrayList;
import java.util.List;

import com.github.tiagolofi.core.JResponse;
import com.github.tiagolofi.core.JTaskRunner;
import com.github.tiagolofi.core.JTool;

public class JInteractiveTaskRunner implements JTaskRunner {
    
    @Override
    public JResponse execute(JTool tool) {
        validate(tool);
        if (!tool.command().isEmpty()) {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(tool.command());
                processBuilder.inheritIO();
                Process process = processBuilder.start();
                return new JResponse(null, process.waitFor());
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
            return new JResponse(null, last.waitFor());
        } catch (Exception e) {
            e.printStackTrace();
            return new JResponse(null, 1);
        }
    }
}
