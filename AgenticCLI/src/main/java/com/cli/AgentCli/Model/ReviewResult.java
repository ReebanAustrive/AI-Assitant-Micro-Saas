package com.cli.AgentCli.Model;

import java.util.List;

public class ReviewResult {
    private List<String> critical;
    private List<String> warnings;
    private List<String> suggestions;
    private double riskScore;
}
