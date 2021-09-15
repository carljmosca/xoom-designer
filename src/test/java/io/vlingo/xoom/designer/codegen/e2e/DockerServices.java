// Copyright © 2012-2021 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.
package io.vlingo.xoom.designer.codegen.e2e;

import io.vlingo.xoom.actors.Logger;
import io.vlingo.xoom.designer.cli.CommandExecutionStep;
import io.vlingo.xoom.designer.cli.TaskExecutionContext;
import io.vlingo.xoom.designer.infrastructure.terminal.ObservableCommandExecutionProcess;
import io.vlingo.xoom.designer.infrastructure.terminal.Terminal;
import io.vlingo.xoom.turbo.ComponentRegistry;

public class DockerServices extends CommandExecutionStep {

  private final CommandObserver observer;

  public static void run() {
    if (!ComponentRegistry.has(DockerServices.class)) {
      new DockerServices(new CommandObserver()).start();
    }
    if (ComponentRegistry.withType(DockerServices.class).isSucceeded()) {
      Logger.basicLogger().info("Docker services are ready");
    } else {
      Logger.basicLogger().warn("Unable to run Docker Services");
    }
  }

  public static void shutdown() {
    if (ComponentRegistry.has(DockerServices.class)) {
      ComponentRegistry.withType(DockerServices.class).stop();
    }
  }

  private DockerServices(final CommandObserver observer) {
    super(new ObservableCommandExecutionProcess(observer));
    ComponentRegistry.register(DockerServices.class, this);
    this.observer = observer;
  }

  private void start() {
    if(isEnabled()) {
      process();
    }
  }

  private void stop() {
    if(isEnabled() && !isStopped()) {
      Logger.basicLogger().info("Stopping Docker services...");
      observer.stop();
    }
  }

  @Override
  protected String formatCommands(final TaskExecutionContext context) {
    final String directoryChangeCommand =
            Terminal.supported().resolveDirectoryChangeCommand(ProjectGenerationTest.testResourcesPath);

    return isNew() ? resolveStartUpCommand(directoryChangeCommand) : resolveShutdownCommand(directoryChangeCommand);
  }

  private String resolveStartUpCommand(final String directoryChangeCommand) {
    return String.format("%s && docker-compose up -d", directoryChangeCommand);
  }

  private String resolveShutdownCommand(final String directoryChangeCommand) {
    return String.format("%s && docker-compose stop", directoryChangeCommand);
  }

  private boolean isSucceeded() {
    return observer.status.equals(ExecutionStatus.SUCCEEDED);
  }

  private boolean isStopped() {
    return observer.status.equals(ExecutionStatus.STOPPED);
  }

  private boolean isNew() {
    return observer.status.equals(ExecutionStatus.NEW);
  }

  private static boolean isEnabled() {
    return Boolean.valueOf(System.getProperty("e2e-docker-services", "false"));
  }

}
