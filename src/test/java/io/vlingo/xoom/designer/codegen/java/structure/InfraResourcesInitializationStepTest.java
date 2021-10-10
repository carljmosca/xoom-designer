package io.vlingo.xoom.designer.codegen.java.structure;

import io.vlingo.xoom.cli.task.TaskExecutionContext;
import io.vlingo.xoom.designer.Profile;
import io.vlingo.xoom.designer.infrastructure.Infrastructure;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InfraResourcesInitializationStepTest {

  private TaskExecutionContext context;
  //private InfraResourcesInitializationStep resourcesLocationStep;

  @Test
  public void testResourceLocationStepWithAlreadyExistingPaths() {
//    resourcesLocationStep.processTaskWith(context);
//    Assertions.assertEquals(Paths.get(System.getProperty("user.dir"), "dist", "designer", "staging"), StagingFolder.path());
  }

  @BeforeEach
  public void setUp() {
    Infrastructure.clear();
    Profile.enableTestProfile();
    this.context = TaskExecutionContext.bare();
    //this.resourcesLocationStep = new InfraResourcesInitializationStep();
  }

  @AfterAll
  public static void clear() {
    Profile.disableTestProfile();
  }

}
