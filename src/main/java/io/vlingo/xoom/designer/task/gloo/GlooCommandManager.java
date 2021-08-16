// Copyright © 2012-2021 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.
package io.vlingo.xoom.designer.task.gloo;

import io.vlingo.xoom.designer.task.*;
import io.vlingo.xoom.designer.task.OptionValue;

import java.util.Arrays;
import java.util.List;

import static io.vlingo.xoom.designer.task.Task.GLOO;

public class GlooCommandManager implements TaskManager<List<String>> {

    private static final int SUB_TASK_INDEX = 1;

    @Override
    public void run(final List<String> args) {
        validateArgs(args);
        final String command = args.get(SUB_TASK_INDEX);
        final SubTask subTask = GLOO.subTaskOf(command);
        runSteps(subTask, args);
    }

    private void runSteps(final SubTask subTask, final List<String> args) {
        final List<OptionValue> optionValues = subTask.findOptionValues(args);

        final TaskExecutionContext context =
                TaskExecutionContext.executedFrom(Agent.TERMINAL)
                        .withOptions(optionValues);

        final List<TaskExecutionStep> steps =
                Arrays.asList(new XoomPropertiesLoadStep(), subTask.commandResolverStep());

        steps.forEach(step -> step.process(context));
    }

    private void validateArgs(final List<String> args) {
        if(args.size() < 1) {
            throw new CommandNotFoundException();
        }
    }

}
