package com.cli.AgentCli.command;


import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Option;
import org.springframework.stereotype.Component;

@Component
public class InitCommands {
    @Command(name = {"ccms", "init", "repo"}, description = "Initialize the context window")
    public void initRepo(@Option(longName = "repo", description = "Gets the repo URL")String repoURL) {

    }
    @Command(name = {"ccms", "inti","arch"}, description = "Intitialize and redirect to the architecture path")
    public void intiArch(@Option(longName = "arch", description = "Path to architecture doc")String archPath){

    }
}
