package com.cli.AgentCli.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity_level")
    private String severity;
}
