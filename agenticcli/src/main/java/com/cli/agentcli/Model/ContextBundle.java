package com.cli.agentcli.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

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
