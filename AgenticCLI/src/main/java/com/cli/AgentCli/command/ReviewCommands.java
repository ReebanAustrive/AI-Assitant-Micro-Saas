package com.cli.AgentCli.command;


import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Option;
import org.springframework.stereotype.Component;

@Component
public class ReviewCommands {
    @Command(name = {"ccms", "review"}, description = "Review the defined PR")
    public void review(@Option(longName = "pr",required = true, description = "Compare the PR differences")Integer pr){

    }
}
