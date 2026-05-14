package com.cli.AgentCli.command;

import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Option;
import org.springframework.stereotype.Component;

@Component
public class ContextCommands {
    @Command(name = {"ccms", "context", "show"}, description = "Will show the current context window")
    public void contextShow(){

    }

    @Command(name = {"ccms", "context", "update"}, description = "Will update the context window")
    public void contextUpdate(){

    }
}
