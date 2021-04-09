// Copyright © 2012-2021 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.xoom.designer.task.projectgeneration.gui.steps;

import io.vlingo.xoom.designer.infrastructure.Infrastructure;
import io.vlingo.xoom.designer.task.TaskExecutionContext;
import io.vlingo.xoom.designer.task.projectgeneration.Terminal;
import io.vlingo.xoom.designer.task.steps.CommandResolverStep;

public class BrowserLaunchCommandResolverStep extends CommandResolverStep {

    @Override
    protected String formatCommands(final TaskExecutionContext context) {
        final String browserLaunchCommand = Terminal.supported().browserLaunchCommand();
        return String.format("%s %s", browserLaunchCommand, Infrastructure.UserInterface.rootContext());
    }

}
