package com.cli.AgentCli.Util;

import com.cli.AgentCli.Model.ContextBundle;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class ContextStorage {
    private final Path filePath = Path.of(System.getProperty("user.home"), ".ccms", "ccms-context.json");
    private final ObjectMapper mapper = new ObjectMapper();
    public ContextStorage(){
        try{
            Files.createDirectories(filePath.getParent());
        } catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }
    public String save(ContextBundle bundle) {
        try{
            String jsonContent = mapper.writeValueAsString(bundle);
            Files.writeString(filePath, jsonContent);
            return "Context is saved successfully!";
        } catch(Exception e){
            return "Context could not be saved!";
        }
    }

    public ContextBundle load() {
        if(!Files.exists(filePath)){
            return null;
        }

        try{
            String jsonContent = Files.readString(filePath);
            return mapper.readValue(jsonContent, ContextBundle.class);
        } catch (Exception ex){
            return null;
        }
    }

}
