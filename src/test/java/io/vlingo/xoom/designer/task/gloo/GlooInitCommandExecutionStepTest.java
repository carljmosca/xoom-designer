// Copyright © 2012-2020 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.xoom.designer.task.gloo;

import io.vlingo.xoom.designer.infrastructure.terminal.CommandRetainer;
import io.vlingo.xoom.designer.infrastructure.terminal.Terminal;
import io.vlingo.xoom.designer.task.TaskExecutionContext;
import io.vlingo.xoom.designer.task.gloo.GlooInitCommandExecutionStep;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GlooInitCommandExecutionStepTest {

    @Test
    public void testGlooInitCommandResolution() {
        final TaskExecutionContext context =
                TaskExecutionContext.withoutOptions();

        final CommandRetainer commandRetainer = new CommandRetainer();

        new GlooInitCommandExecutionStep(commandRetainer).process(context);

        final String[] commandsSequence = commandRetainer.retainedCommandsSequence().get(0);
        Assertions.assertEquals(Terminal.supported().initializationCommand(), commandsSequence[0]);
        Assertions.assertEquals(Terminal.supported().parameter(), commandsSequence[1]);
        Assertions.assertEquals("glooctl install gateway", commandsSequence[2]);
    }

}
