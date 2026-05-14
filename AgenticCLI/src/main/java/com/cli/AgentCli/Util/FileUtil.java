package com.cli.AgentCli.Util;

import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileUtil {
    public String readFile(String path){
        try {
            String normalizedPath = path.replace("\\", "/");
            if (normalizedPath.length() > 1 && normalizedPath.charAt(1) != ':') {
                normalizedPath = normalizedPath.charAt(0) + ":/" + normalizedPath.substring(1);
            }
            return Files.readString(Path.of(normalizedPath));
        } catch (Exception e) {
            System.err.println("FileUtil ERROR: " + e.getClass().getName() + " - " + e.getMessage());
            return null;
        }
    }
}
