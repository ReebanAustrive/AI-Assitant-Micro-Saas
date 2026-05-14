package com.cli.AgentCli.Model;

import com.cli.AgentCli.Enums.Severity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rule {
    private String id;

    private String title;
    private String description;

    private Severity severity;
}
