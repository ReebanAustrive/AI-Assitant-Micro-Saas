package com.cli.agentcli;

import ch.qos.logback.core.status.NopStatusListener;
import com.cli.agentcli.Enums.Severity;
import com.cli.agentcli.Model.ContextBundle;
import com.cli.agentcli.Model.PullRequest;
import com.cli.agentcli.Model.ReviewResult;
import com.cli.agentcli.Model.Rule;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.aot.hint.BindingReflectionHintsRegistrar;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.shell.core.command.CommandNotFoundException;

@SpringBootApplication
@ImportRuntimeHints(AgentApplication.NativeHints.class)
public class AgentApplication {

	/** Reflection metadata the GraalVM native image needs at runtime. */
	static class NativeHints implements RuntimeHintsRegistrar {
		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			// Jackson (de)serializes these models via reflection (context file, Gemini response).
			new BindingReflectionHintsRegistrar().registerReflectionHints(hints.reflection(),
					ContextBundle.class, Rule.class, Severity.class, ReviewResult.class, PullRequest.class);
			// Logback instantiates the status listener by class name.
			hints.reflection().registerType(NopStatusListener.class, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
		}
	}

	public static void main(String[] args) {
		// Must run before logback initializes: suppresses its internal status dump,
		// which native images trigger via an "unknown version" warning.
		System.setProperty("logback.statusListenerClass", "ch.qos.logback.core.status.NopStatusListener");
		// Spring Boot's own status listener re-prints recent WARNs (e.g. that version warning); drop them first.
		if (LoggerFactory.getILoggerFactory() instanceof LoggerContext logbackContext) {
			logbackContext.getStatusManager().clear();
		}

		try {
			SpringApplication.run(AgentApplication.class, normalizeArgs(args));
		} catch (SpringApplication.AbandonedRunException e) {
			// Thrown on purpose by Spring AOT processing (processAot / nativeCompile); must propagate.
			throw e;
		} catch (Throwable e) {
			if (isCommandNotFound(e)) {
				System.err.println("Unknown command. Run 'ccms help' to see available commands.");
			} else {
				System.err.println("Error: " + rootMessage(e));
			}
			System.exit(1);
		}
	}

	private static String[] normalizeArgs(String[] args) {
		if (args.length == 0) {
			return new String[] {"help"};
		}
		return switch (args[0]) {
			case "--help", "-h" -> new String[] {"help"};
			case "--version", "-v" -> new String[] {"version"};
			default -> args;
		};
	}

	private static boolean isCommandNotFound(Throwable e) {
		for (Throwable t = e; t != null; t = t.getCause()) {
			if (t instanceof CommandNotFoundException) {
				return true;
			}
		}
		return false;
	}

	private static String rootMessage(Throwable e) {
		Throwable root = e;
		while (root.getCause() != null) {
			root = root.getCause();
		}
		return root.getMessage() != null ? root.getMessage() : root.getClass().getSimpleName();
	}
}
