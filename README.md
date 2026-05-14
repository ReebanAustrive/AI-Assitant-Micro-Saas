# CCMS AI CLI

A context-aware AI-powered code review CLI built with Spring Shell 4, Spring Boot 4, and Claude AI. Feed it your project's architecture, rules, and codebase once — then get intelligent PR reviews that stay strictly within your project's context.

---

## What It Does

CCMS AI CLI fetches any GitHub PR, analyzes the diff against your project's stored context, and returns structured warnings, suggestions, and a production risk score — all from your terminal.

```
shell:> ccms init --repo https://github.com/org/ccms --arch ./docs/arch.md --rules ./ccms-rules.json
✅ Context initialized and saved to ~/.ccms/ccms-context.json

shell:> ccms review --pr 42

📋 PR #42 — "Add user authentication layer"
Author: reebanpro | Files changed: 7 | +234 -12

🔴 CRITICAL (2)
  → AuthService.java:34 — No null check on userToken, will NPE in production
  → SecurityConfig.java:78 — Removes existing CORS rule, breaks frontend

🟡 WARNINGS (3)
  → UserRepository.java:56 — N+1 query detected, will degrade under load
  → New package detected: io.jsonwebtoken — not in existing codebase
  → PasswordEncoder changed from BCrypt to SHA256 — weaker security

🟢 SUGGESTIONS (2)
  → AuthService.java:89 — Extract token validation to a separate method
  → Consider adding @Transactional to saveUser()

📊 Production Risk Score: 7.2/10 — HIGH RISK
Recommendation: Do not merge without addressing critical issues
```

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| CLI Framework | Spring Shell 4 |
| Backend | Spring Boot 4 |
| Reactive HTTP | Spring WebFlux (WebClient) |
| AI Analysis | Claude API (claude-sonnet-4-20250514) |
| GitHub Integration | GitHub REST API v3 |
| Context Storage | Local JSON (~/.ccms/) |
| Terminal Output | JLine (colors, tables) |
| Build Tool | Gradle |
| Java Version | Java 21 |

---

## Project Structure

```
src/
└── main/
    └── java/
        └── com/cli/AgentCli/
            ├── AgentCliApplication.java
            ├── command/
            │   ├── InitCommand.java
            │   ├── ReviewCommand.java
            │   ├── RulesCommand.java
            │   └── ContextCommand.java
            ├── service/
            │   ├── ContextService.java
            │   ├── GitHubService.java
            │   ├── ClaudeService.java
            │   └── RulesService.java
            ├── model/
            │   ├── ContextBundle.java
            │   ├── PullRequest.java
            │   ├── ReviewResult.java
            │   └── Rule.java
            ├── config/
            │   ├── WebClientConfig.java
            │   └── ClaudeConfig.java
            └── util/
                ├── FileUtil.java
                ├── ContextStorage.java
                └── OutputRenderer.java
```

---

## Prerequisites

- Java 21+
- Gradle
- GitHub Personal Access Token
- Claude API Key (get one at [console.anthropic.com](https://console.anthropic.com))

---

## Setup

**1. Clone the repository**
```bash
git clone https://github.com/your-username/agent-cli.git
cd agent-cli
```

**2. Configure API keys in `application.properties`**
```properties
github.token=your_github_personal_access_token
claude.api.key=your_claude_api_key
spring.shell.interactive.enabled=true
```

**3. Build and run**
```bash
./gradlew clean bootRun
```

---

## Commands

### `ccms init`
Initializes the context window with your project's architecture, codebase, and rules.

```
shell:> ccms init --repo <github-repo-url> --arch <path-to-arch-doc> --rules <path-to-rules-json>
```

| Option | Required | Description |
|--------|----------|-------------|
| `--repo` | ✅ | GitHub repository URL |
| `--arch` | ✅ | Path to your architecture markdown doc |
| `--rules` | ✅ | Path to your custom rules JSON file |

---

### `ccms review`
Fetches a PR by number and runs AI analysis against your stored context.

```
shell:> ccms review --pr <pr-number>
```

| Option | Required | Description |
|--------|----------|-------------|
| `--pr` | ✅ | Pull request number to review |

---

### `ccms rules add`
Adds a custom rule to the context window.

```
shell:> ccms rules add --rule "All service methods must have @Transactional"
```

### `ccms rules remove`
Removes a rule by its ID.

```
shell:> ccms rules remove --rule CCMS-001
```

### `ccms rules list`
Lists all rules currently in the context window.

```
shell:> ccms rules list
```

---

### `ccms context show`
Displays a summary of the current context bundle.

```
shell:> ccms context show

📦 Context Summary
Repo: https://github.com/org/ccms
Rules: 5 active rules
Last Updated: 2026-05-12 14:43:04
```

### `ccms context update`
Re-syncs the context with the latest state of the repo and docs.

```
shell:> ccms context update
```

---

## Custom Rules Format

Create a `ccms-rules.json` file with your project-specific rules:

```json
{
  "rules": [
    {
      "id": "CCMS-001",
      "description": "All service methods must have @Transactional",
      "severity": "WARNING"
    },
    {
      "id": "CCMS-002",
      "description": "No direct database calls outside repository layer",
      "severity": "CRITICAL"
    },
    {
      "id": "CCMS-003",
      "description": "All new packages must be approved before merging",
      "severity": "WARNING"
    }
  ]
}
```

**Severity levels:** `CRITICAL` | `WARNING` | `SUGGESTION`

---

## How Context Engineering Works

CCMS AI CLI uses a **bounded context** approach — the AI is strictly constrained to your project's knowledge:

1. `ccms init` builds a context bundle from your architecture doc, rules, and repo
2. The bundle is stored locally at `~/.ccms/ccms-context.json`
3. Every `ccms review` call loads the bundle and injects it as the AI's system prompt
4. The AI is instructed to **only** evaluate code against this context — no generic advice
5. Results are always specific to your codebase patterns and rules

---

## What the AI Checks

| Check | Description |
|-------|-------------|
| Production risk | Code changes likely to cause failures in prod |
| API contract breaks | Changes that break existing endpoint contracts |
| Null pointer risks | Missing null checks on critical paths |
| Performance issues | N+1 queries, missing indexes, blocking calls |
| Security vulnerabilities | Weak encoding, exposed secrets, auth gaps |
| New package detection | Flags any new dependency not in existing codebase |
| Custom rule violations | Violations of your project-specific rules |
| Bug detection | Logic errors, edge cases, missing error handling |
| Maintainability | Code that will make future changes harder |

---

## Context Storage

Context is stored locally on your machine at:
```
~/.ccms/ccms-context.json
```

This file is created automatically on first `ccms init`. It persists across sessions so you only need to initialize once per project.

---

## Roadmap

- [x] Context initialization and storage
- [x] Custom rules engine
- [ ] GitHub PR fetcher via WebClient
- [ ] Claude API integration
- [ ] JLine colored output renderer
- [ ] Multi-project context support
- [ ] CI/CD integration (run as GitHub Action)
- [ ] Export review report as PDF/Markdown

---

## Contributing

1. Fork the repo
2. Create a feature branch (`git checkout -b feature/my-feature`)
3. Commit your changes
4. Push and open a PR
5. Run `ccms review --pr <your-pr-number>` to self-review before asking for a human review 😄

---

## License

MIT License — see [LICENSE](LICENSE) for details.

---

Built with ☕ and Spring Shell 4 by Reeban
