package io.vlingo.xoom.designer.task;

import io.vlingo.xoom.designer.infrastructure.userinterface.UserInterfaceManager;
import io.vlingo.xoom.designer.task.docker.DockerCommandManager;
import io.vlingo.xoom.designer.task.version.VersionDisplayManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static io.vlingo.xoom.designer.task.SubTask.*;
import static io.vlingo.xoom.designer.task.Task.DOCKER;

public class TaskTest {

    @Test
    public void testTaskRetrievalByCommand() {
        Assertions.assertEquals(UserInterfaceManager.class, Task.of("gui", Arrays.asList("0")).getClass());
        Assertions.assertEquals(DockerCommandManager.class, Task.of("dOckEr", Arrays.asList("0")).getClass());
        Assertions.assertEquals(VersionDisplayManager.class, Task.of("-version", Arrays.asList("0")).getClass());
    }

    @Test
    public void testTaskRetrievalByUnknownCommand() {
        Assertions.assertThrows(UnknownCommandException.class, () -> {
            Task.of("start", Arrays.asList("0"));
        });
    }

    @Test
    public void testDockerSubTaskRetrieval() {
        Assertions.assertEquals(DOCKER_PUSH, DOCKER.subTaskOf("push"));
        Assertions.assertEquals(DOCKER_PACKAGE, DOCKER.subTaskOf("package"));
        Assertions.assertEquals(DOCKER_STATUS, DOCKER.subTaskOf("status"));
    }

    @Test
    public void testUnknownSubTaskRetrieval() {
        Assertions.assertThrows(UnknownCommandException.class, () -> {
            DOCKER.subTaskOf("reset");
        });
    }
}
