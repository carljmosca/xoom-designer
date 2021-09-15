// Copyright © 2012-2021 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.xoom.designer.cli;

import io.vlingo.xoom.designer.cli.docker.DockerPackageCommandExecutionStep;
import io.vlingo.xoom.designer.cli.docker.DockerPushCommandExecutionStep;
import io.vlingo.xoom.designer.cli.docker.DockerStatusCommandExecutionStep;
import io.vlingo.xoom.designer.cli.gloo.GlooInitCommandExecutionStep;
import io.vlingo.xoom.designer.cli.gloo.GlooRouteCommandExecutionStep;
import io.vlingo.xoom.designer.cli.gloo.GlooSuspendCommandExecutionStep;
import io.vlingo.xoom.designer.cli.k8s.KubernetesPushCommandExecutionStep;
import io.vlingo.xoom.designer.infrastructure.terminal.DefaultCommandExecutionProcess;

import java.util.Arrays;
import java.util.List;

import static io.vlingo.xoom.designer.cli.OptionName.CURRENT_DIRECTORY;
import static io.vlingo.xoom.designer.cli.OptionName.TAG;

public enum SubTask {

    DOCKER_STATUS(Task.DOCKER, "status",
            new DockerStatusCommandExecutionStep(new DefaultCommandExecutionProcess()),
            Option.required(CURRENT_DIRECTORY)),

    DOCKER_PACKAGE(Task.DOCKER, "package",
            new DockerPackageCommandExecutionStep(new DefaultCommandExecutionProcess()),
            Option.required(CURRENT_DIRECTORY),
            Option.of(TAG, "latest")),

    DOCKER_PUSH(Task.DOCKER, "push",
            new DockerPushCommandExecutionStep(new DefaultCommandExecutionProcess()),
            Option.required(CURRENT_DIRECTORY),
            Option.of(TAG, "latest")),

    K8S_PUSH(Task.K8S, "push",
            new KubernetesPushCommandExecutionStep(new DefaultCommandExecutionProcess())),

    GLOO_INIT(Task.GLOO, "init",
            new GlooInitCommandExecutionStep(new DefaultCommandExecutionProcess()),
            Option.required(CURRENT_DIRECTORY)),

    GLOO_ROUTE(Task.GLOO, "route",
            new GlooRouteCommandExecutionStep(new DefaultCommandExecutionProcess()),
            Option.required(CURRENT_DIRECTORY)),

    GLOO_SUSPEND(Task.GLOO, "suspend",
            new GlooSuspendCommandExecutionStep(new DefaultCommandExecutionProcess()),
            Option.required(CURRENT_DIRECTORY));

    private final Task parentTask;
    private final String command;
    private final TaskExecutionStep commandResolverStep;
    private final List<Option> options;

    SubTask(final Task parentTask,
            final String command,
            final TaskExecutionStep commandResolverStep,
            final Option ...options) {
        this.parentTask = parentTask;
        this.command = command;
        this.commandResolverStep = commandResolverStep;
        this.options = Arrays.asList(options);
    }

    public List<OptionValue> findOptionValues(final List<String> args) {
        return OptionValue.resolveValues(this.options, args);
    }

    public TaskExecutionStep commandResolverStep() {
        return commandResolverStep;
    }

    protected boolean triggeredBy(final String command) {
        return this.command.trim().equalsIgnoreCase(command);
    }

    protected boolean isChildFrom(final Task task) {
        return this.parentTask.equals(task);
    }

}
