package com.cli.AgentCli.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContextBundle{
    private String repoUrl;
    private String architectureContent;
    private List<Rule> rules;
    private LocalDateTime lastUpdated;
}
