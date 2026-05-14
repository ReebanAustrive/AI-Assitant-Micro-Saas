package com.cli.AgentCli.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ContextBundle {
    private String repoUrl;
    private String architectureContent;
    private List<Rule> rules;
    private LocalDateTime lastUpdated;
}
