package com.cli.AgentCli.command;

import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Option;
import org.springframework.stereotype.Component;

@Component
public class Rulescommands {
    @Command(name = {"ccms", "rules", "list"},description = "Set the rules into the context window")
    public void ruleList(){

    }

    @Command(name = {"ccms", "rules","add"}, description = "Set the rules into the context window")
    public void ruleAdd(@Option(longName = "rule", required = true, description = "Will add the new rule into the context window")String rule){

    }

    @Command(name = {"ccms", "rules", "remove"}, description = "Set the rules into the contest window")
    public void ruleRemove(@Option(longName = "rule", required = true, description = "Set the rules into the context window")String rule){

    }
}
