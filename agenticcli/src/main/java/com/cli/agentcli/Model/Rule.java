package com.cli.agentcli.Model;

import com.cli.agentcli.Enums.Severity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rule {
    private String id;

    private String title;
    private String description;

    private Severity severity;
}
