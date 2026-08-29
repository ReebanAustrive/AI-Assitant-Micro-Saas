package com.cli.AgentCli.command;

import com.cli.AgentCli.Model.ContextBundle;
import com.cli.AgentCli.Service.ContextService;
import com.cli.AgentCli.Util.ContextStorage;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Option;
import org.springframework.stereotype.Component;

@Component
public class ContextCommands {
    private ContextStorage contextStorage;
    private ContextService contextService;

    public ContextCommands(ContextStorage contextStorage){
        this.contextStorage = contextStorage;
    }

    @Command(name = {"ccms", "context", "show"}, description = "Will show the current context window")
    public String contextShow(){
        ContextBundle bundle = contextStorage.load();

        if(bundle == null){
            return "No context found, first initialize your project. RUN: ccms help";
        }

        return """
       --- CCMS Context Summary ---
       Repository: %s
       Rules Count: %d
       Last Updated: %s
       """.formatted(
                bundle.getRepoUrl(),
                bundle.getRules() != null ? bundle.getRules().size() : 0,
                bundle.getArchitectureContent(),
                bundle.getLastUpdated()
        );
    }

    @Command(name = {"ccms", "context", "update"}, description = "Will update the context window")
    public String contextUpdate(String repoUrl,String archPath, String rulePath){
        ContextBundle bundle = contextStorage.load();

        if(bundle == null){
            return "No context found, first iinitialize your project. RUN: ccms help";
        }

        contextService.initContext(repoUrl, archPath, rulePath);
        return "Context updated successfully";
    }
}
