package com.cli.agentcli.command;

import com.cli.agentcli.Model.ContextBundle;
import com.cli.agentcli.Service.ContextService;
import com.cli.agentcli.Util.ContextStorage;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Option;
import org.springframework.stereotype.Component;

@Component
public class ContextCommands {
    private final ContextStorage contextStorage;
    private final ContextService contextService;

    public ContextCommands(ContextStorage contextStorage, ContextService contextService){
        this.contextStorage = contextStorage;
        this.contextService = contextService;
    }

    @Command(name = {"context", "show"}, description = "Will show the current context window")
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
                bundle.getLastUpdated()
        );
    }

    @Command(name = {"context", "update"}, description = "Will update the context window")
    public String contextUpdate(@Option(longName = "repo", required = false, description = "New repo URL (defaults to the current one)") String repoUrl,
                                @Option(longName = "arch", required = true, description = "Path for architecture document") String archPath,
                                @Option(longName = "rule", required = false, description = "Path for rules JSON file") String rulePath){
        ContextBundle bundle = contextStorage.load();

        if(bundle == null){
            return "No context found, first initialize your project. RUN: ccms help";
        }

        String repo = (repoUrl == null || repoUrl.isBlank()) ? bundle.getRepoUrl() : repoUrl;
        return contextService.initContext(repo, archPath, rulePath);
    }
}
