package com.cli.AgentCli.Service;

import com.cli.AgentCli.Model.ContextBundle;
import com.cli.AgentCli.Model.Rule;
import com.cli.AgentCli.Util.ContextStorage;
import com.cli.AgentCli.Util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContextService {
    @Autowired
    private ContextStorage contextStorage;
    @Autowired
    private FileUtil fileUtil;
    private final ObjectMapper mapper = new ObjectMapper();
    public String initContext(String repoUrl, String archPath, String rulesPath){
        List<Rule> ruleList = new ArrayList<>();
       try{
           String archString = fileUtil.readFile(archPath);
           System.out.println("DEBUG archPath: " + archPath);
           if (rulesPath != null && !rulesPath.isEmpty()) {
               String rulesString = fileUtil.readFile(rulesPath);
               ruleList = mapper.readValue(rulesString, new TypeReference<List<Rule>>() {});
           }

           ContextBundle  bundle = new ContextBundle();
           bundle.setRepoUrl(repoUrl);
           bundle.setArchitectureContent(archString);
           bundle.setRules(ruleList);
           bundle.setLastUpdated(LocalDateTime.now());
           contextStorage.save(bundle);
           return "Context saved successfully!";
       } catch (Exception e){
           return "Failed to initialize with context window. Try again!";
       }
    }

}
