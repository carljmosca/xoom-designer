package io.vlingo.xoom.designer.task.projectgeneration.code.java.model;

import io.vlingo.xoom.codegen.CodeGenerationContext;
import io.vlingo.xoom.codegen.TextExpectation;
import io.vlingo.xoom.codegen.content.Content;
import io.vlingo.xoom.codegen.dialect.Dialect;
import io.vlingo.xoom.codegen.parameter.CodeGenerationParameter;
import io.vlingo.xoom.codegen.parameter.CodeGenerationParameters;
import io.vlingo.xoom.codegen.template.OutputFile;
import io.vlingo.xoom.designer.task.projectgeneration.Label;
import io.vlingo.xoom.designer.task.projectgeneration.code.CodeGenerationTest;
import io.vlingo.xoom.designer.task.projectgeneration.code.java.JavaTemplateStandard;
import io.vlingo.xoom.designer.task.projectgeneration.code.java.projections.ProjectionType;
import io.vlingo.xoom.designer.task.projectgeneration.code.java.storage.StorageType;
import io.vlingo.xoom.turbo.OperatingSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static io.vlingo.xoom.designer.task.projectgeneration.Label.METHOD_PARAMETER;

public class ModelGenerationStepTest extends CodeGenerationTest {

  @Test
  public void testThatEventBasedStatefulModelIsGenerated()  {
    final CodeGenerationParameters parameters =
            CodeGenerationParameters.from(CodeGenerationParameter.of(Label.PACKAGE, "io.vlingo.xoomapp"),
                    CodeGenerationParameter.of(Label.STORAGE_TYPE, StorageType.STATE_STORE),
                    CodeGenerationParameter.of(Label.PROJECTION_TYPE, ProjectionType.EVENT_BASED),
                    CodeGenerationParameter.of(Label.DIALECT, Dialect.JAVA),
                    CodeGenerationParameter.of(Label.CQRS, true),
                    authorAggregate(), nameValueObject(), rankValueObject());

    final CodeGenerationContext context =
            CodeGenerationContext.with(parameters).contents(contents());

    final ModelGenerationStep modelGenerationStep = new ModelGenerationStep();

    Assertions.assertTrue(modelGenerationStep.shouldProcess(context));

    modelGenerationStep.process(context);

    final Content authorProtocol = context.findContent(JavaTemplateStandard.AGGREGATE_PROTOCOL, "Author");
    final Content authorEntity = context.findContent(JavaTemplateStandard.AGGREGATE, "AuthorEntity");
    final Content authorState = context.findContent(JavaTemplateStandard.AGGREGATE_STATE, "AuthorState");
    final Content authorRegistered = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorRegistered");
    final Content authorRanked = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorRanked");
    final Content authorRelated = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorRelated");
    final Content authorsRelated = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorsRelated");
    final Content authorUnrelated = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorUnrelated");

    Assertions.assertEquals(10, context.contents().size());
    Assertions.assertTrue(authorProtocol.contains(TextExpectation.onJava().read("author-protocol")));
    Assertions.assertTrue(authorEntity.contains(TextExpectation.onJava().read("event-based-stateful-author-entity")));
    Assertions.assertTrue(authorState.contains(TextExpectation.onJava().read("stateful-author-state")));
    Assertions.assertTrue(authorRegistered.contains(TextExpectation.onJava().read("author-registered")));
    Assertions.assertTrue(authorRanked.contains(TextExpectation.onJava().read("author-ranked")));
    Assertions.assertTrue(authorRelated.contains(TextExpectation.onJava().read("author-related")));
    Assertions.assertTrue(authorsRelated.contains(TextExpectation.onJava().read("authors-related")));
    Assertions.assertTrue(authorUnrelated.contains(TextExpectation.onJava().read("author-unrelated")));
  }

  @Test
  public void testThatOperationBasedStatefulModelIsGenerated()  {
    final CodeGenerationParameters parameters =
            CodeGenerationParameters.from(CodeGenerationParameter.of(Label.PACKAGE, "io.vlingo.xoomapp"),
                    CodeGenerationParameter.of(Label.STORAGE_TYPE, StorageType.STATE_STORE),
                    CodeGenerationParameter.of(Label.PROJECTION_TYPE, ProjectionType.OPERATION_BASED),
                    CodeGenerationParameter.of(Label.DIALECT, Dialect.JAVA),
                    CodeGenerationParameter.of(Label.CQRS, true),
                    authorAggregate(), nameValueObject(), rankValueObject());

    final CodeGenerationContext context =
            CodeGenerationContext.with(parameters).contents(contents());

    final ModelGenerationStep modelGenerationStep = new ModelGenerationStep();

    Assertions.assertTrue(modelGenerationStep.shouldProcess(context));

    modelGenerationStep.process(context);

    final Content authorProtocol = context.findContent(JavaTemplateStandard.AGGREGATE_PROTOCOL, "Author");
    final Content authorEntity = context.findContent(JavaTemplateStandard.AGGREGATE, "AuthorEntity");
    final Content authorState = context.findContent(JavaTemplateStandard.AGGREGATE_STATE, "AuthorState");
    final Content authorRegistered = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorRegistered");
    final Content authorRanked = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorRanked");
    final Content authorRelated = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorRelated");
    final Content authorsRelated = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorsRelated");
    final Content authorUnrelated = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorUnrelated");

    Assertions.assertEquals(10, context.contents().size());
    Assertions.assertTrue(authorProtocol.contains(TextExpectation.onJava().read("author-protocol")));
    Assertions.assertTrue(authorEntity.contains(TextExpectation.onJava().read("operation-based-stateful-author-entity")));
    Assertions.assertTrue(authorState.contains(TextExpectation.onJava().read("stateful-author-state")));
    Assertions.assertTrue(authorRegistered.contains(TextExpectation.onJava().read("author-registered")));
    Assertions.assertTrue(authorRanked.contains(TextExpectation.onJava().read("author-ranked")));
    Assertions.assertTrue(authorRelated.contains(TextExpectation.onJava().read("author-related")));
    Assertions.assertTrue(authorsRelated.contains(TextExpectation.onJava().read("authors-related")));
    Assertions.assertTrue(authorUnrelated.contains(TextExpectation.onJava().read("author-unrelated")));
  }

  @Test
  public void testThatOperationBasedStatefulSingleModelIsGenerated()  {
    final CodeGenerationParameters parameters =
            CodeGenerationParameters.from(CodeGenerationParameter.of(Label.PACKAGE, "io.vlingo.xoomapp"),
                    CodeGenerationParameter.of(Label.STORAGE_TYPE, StorageType.STATE_STORE),
                    CodeGenerationParameter.of(Label.PROJECTION_TYPE, ProjectionType.NONE),
                    CodeGenerationParameter.of(Label.DIALECT, Dialect.JAVA),
                    CodeGenerationParameter.of(Label.CQRS, false),
                    authorAggregate(), nameValueObject(), rankValueObject());

    final CodeGenerationContext context =
            CodeGenerationContext.with(parameters).contents(contents());

    final ModelGenerationStep modelGenerationStep = new ModelGenerationStep();

    Assertions.assertTrue(modelGenerationStep.shouldProcess(context));

    modelGenerationStep.process(context);

    final Content authorProtocol = context.findContent(JavaTemplateStandard.AGGREGATE_PROTOCOL, "Author");
    final Content authorEntity = context.findContent(JavaTemplateStandard.AGGREGATE, "AuthorEntity");
    final Content authorState = context.findContent(JavaTemplateStandard.AGGREGATE_STATE, "AuthorState");
    final Content authorRegistered = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorRegistered");
    final Content authorRanked = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorRanked");
    final Content authorRelated = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorRelated");
    final Content authorsRelated = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorsRelated");
    final Content authorUnrelated = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorUnrelated");

    Assertions.assertEquals(10, context.contents().size());
    Assertions.assertTrue(authorProtocol.contains(TextExpectation.onJava().read("author-protocol-for-single-model")));
    Assertions.assertTrue(authorEntity.contains(TextExpectation.onJava().read("operation-based-stateful-single-model-author-entity")));
    Assertions.assertTrue(authorState.contains(TextExpectation.onJava().read("stateful-author-state")));
    Assertions.assertTrue(authorRegistered.contains(TextExpectation.onJava().read("author-registered")));
    Assertions.assertTrue(authorRanked.contains(TextExpectation.onJava().read("author-ranked")));
    Assertions.assertTrue(authorRelated.contains(TextExpectation.onJava().read("author-related")));
    Assertions.assertTrue(authorsRelated.contains(TextExpectation.onJava().read("authors-related")));
    Assertions.assertTrue(authorUnrelated.contains(TextExpectation.onJava().read("author-unrelated")));
  }

  @Test
  public void testThatSourcedModelIsGenerated() {
    final CodeGenerationParameters parameters =
            CodeGenerationParameters.from(CodeGenerationParameter.of(Label.PACKAGE, "io.vlingo.xoomapp"),
                    CodeGenerationParameter.of(Label.STORAGE_TYPE, StorageType.JOURNAL),
                    CodeGenerationParameter.of(Label.PROJECTION_TYPE, ProjectionType.EVENT_BASED),
                    CodeGenerationParameter.of(Label.DIALECT, Dialect.JAVA),
                    CodeGenerationParameter.of(Label.CQRS, true),
                    authorAggregate(), nameValueObject(), rankValueObject());

    final CodeGenerationContext context =
            CodeGenerationContext.with(parameters).contents(contents());

    final ModelGenerationStep modelGenerationStep = new ModelGenerationStep();

    Assertions.assertTrue(modelGenerationStep.shouldProcess(context));

    modelGenerationStep.process(context);

    final Content authorProtocol = context.findContent(JavaTemplateStandard.AGGREGATE_PROTOCOL, "Author");
    final Content authorEntity = context.findContent(JavaTemplateStandard.AGGREGATE, "AuthorEntity");
    final Content authorState = context.findContent(JavaTemplateStandard.AGGREGATE_STATE, "AuthorState");
    final Content authorRegistered = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorRegistered");
    final Content authorRanked = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorRanked");
    final Content authorRelated = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorRelated");
    final Content authorsRelated = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorsRelated");
    final Content authorUnrelated = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorUnrelated");

    Assertions.assertEquals(10, context.contents().size());
    Assertions.assertTrue(authorProtocol.contains(TextExpectation.onJava().read("author-protocol")));
    Assertions.assertTrue(authorEntity.contains(TextExpectation.onJava().read("sourced-author-entity")));
    Assertions.assertTrue(authorState.contains(TextExpectation.onJava().read("sourced-author-state")));
    Assertions.assertTrue(authorRegistered.contains(TextExpectation.onJava().read("author-registered")));
    Assertions.assertTrue(authorRanked.contains(TextExpectation.onJava().read("author-ranked")));
    Assertions.assertTrue(authorRelated.contains(TextExpectation.onJava().read("author-related")));
    Assertions.assertTrue(authorsRelated.contains(TextExpectation.onJava().read("authors-related")));
    Assertions.assertTrue(authorUnrelated.contains(TextExpectation.onJava().read("author-unrelated")));
  }

  @Test
  public void testThatSourcedSingleModelIsGenerated()  {
    final CodeGenerationParameters parameters =
            CodeGenerationParameters.from(CodeGenerationParameter.of(Label.PACKAGE, "io.vlingo.xoomapp"),
                    CodeGenerationParameter.of(Label.STORAGE_TYPE, StorageType.JOURNAL),
                    CodeGenerationParameter.of(Label.PROJECTION_TYPE, ProjectionType.EVENT_BASED),
                    CodeGenerationParameter.of(Label.DIALECT, Dialect.JAVA),
                    CodeGenerationParameter.of(Label.CQRS, false),
                    authorAggregate(), nameValueObject(), rankValueObject());

    final CodeGenerationContext context =
            CodeGenerationContext.with(parameters).contents(contents());

    final ModelGenerationStep modelGenerationStep = new ModelGenerationStep();

    Assertions.assertTrue(modelGenerationStep.shouldProcess(context));

    modelGenerationStep.process(context);

    final Content authorProtocol = context.findContent(JavaTemplateStandard.AGGREGATE_PROTOCOL, "Author");
    final Content authorEntity = context.findContent(JavaTemplateStandard.AGGREGATE, "AuthorEntity");
    final Content authorState = context.findContent(JavaTemplateStandard.AGGREGATE_STATE, "AuthorState");
    final Content authorRegistered = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorRegistered");
    final Content authorRanked = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorRanked");

    Assertions.assertEquals(10, context.contents().size());
    Assertions.assertTrue(authorProtocol.contains(TextExpectation.onJava().read("author-protocol-for-single-model")));
    Assertions.assertTrue(authorEntity.contains(TextExpectation.onJava().read("sourced-single-model-author-entity")));
    Assertions.assertTrue(authorState.contains(TextExpectation.onJava().read("sourced-author-state")));
    Assertions.assertTrue(authorRegistered.contains(TextExpectation.onJava().read("author-registered")));
    Assertions.assertTrue(authorRanked.contains(TextExpectation.onJava().read("author-ranked")));
  }

  @Test
  @Disabled
  public void testThatStatefulModelIsGeneratedOnKotlin() {
    final CodeGenerationParameters parameters =
            CodeGenerationParameters.from(CodeGenerationParameter.of(Label.PACKAGE, "io.vlingo.xoomapp"),
                    CodeGenerationParameter.of(Label.STORAGE_TYPE, StorageType.STATE_STORE),
                    CodeGenerationParameter.of(Label.PROJECTION_TYPE, ProjectionType.EVENT_BASED),
                    CodeGenerationParameter.of(Label.DIALECT, Dialect.KOTLIN),
                    authorAggregate());

    final CodeGenerationContext context =
            CodeGenerationContext.with(parameters).contents(contents());

    final ModelGenerationStep modelGenerationStep = new ModelGenerationStep();

    Assertions.assertTrue(modelGenerationStep.shouldProcess(context));

    modelGenerationStep.process(context);

    final Content author = context.findContent(JavaTemplateStandard.AGGREGATE_PROTOCOL, "Author");
    final Content authorEntity = context.findContent(JavaTemplateStandard.AGGREGATE, "AuthorEntity");
    final Content authorState = context.findContent(JavaTemplateStandard.AGGREGATE_STATE, "AuthorState");
    final Content authorRegistered = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorRegistered");
    final Content authorRanked = context.findContent(JavaTemplateStandard.DOMAIN_EVENT, "AuthorRanked");

    Assertions.assertEquals(9, context.contents().size());
    Assertions.assertTrue(author.contains("interface Author "));
    Assertions.assertTrue(author.contains("val _address = stage.addressFactory().uniquePrefixedWith(\"g-\") : Address"));
    Assertions.assertTrue(author.contains("val _author = stage.actorFor(Author::class.java, Definition.has(AuthorEntity::class.java, Definition.parameters(_address.idString())), _address) : Author"));
    Assertions.assertTrue(author.contains("return _author.withName(name)"));
    Assertions.assertTrue(authorEntity.contains("public class AuthorEntity : StatefulEntity<AuthorState>, Author"));
    Assertions.assertTrue(authorEntity.contains("public fun withName(final Name name): Completes<AuthorState>"));
    Assertions.assertTrue(authorEntity.contains("val stateArg: AuthorState = state.withName(name)"));
    Assertions.assertTrue(authorEntity.contains("return apply(stateArg, AuthorRegistered(stateArg)){state}"));
    Assertions.assertTrue(authorState.contains("class AuthorState"));
    Assertions.assertTrue(authorState.contains("val id: String;"));
    Assertions.assertTrue(authorState.contains("val name: Name;"));
    Assertions.assertTrue(authorState.contains("val rank: Rank;"));
    Assertions.assertTrue(authorRegistered.contains("class AuthorRegistered : IdentifiedDomainEvent"));
    Assertions.assertTrue(authorRegistered.contains("val id: String;"));
    Assertions.assertTrue(authorRegistered.contains("val name: Name;"));
    Assertions.assertTrue(authorRanked.contains("class AuthorRanked : IdentifiedDomainEvent"));
    Assertions.assertTrue(authorRanked.contains("val id: String;"));
    Assertions.assertTrue(authorRanked.contains("val rank: Rank;"));
  }

  private CodeGenerationParameter authorAggregate() {
    final CodeGenerationParameter idField =
            CodeGenerationParameter.of(Label.STATE_FIELD, "id")
                    .relate(Label.FIELD_TYPE, "String");

    final CodeGenerationParameter nameField =
            CodeGenerationParameter.of(Label.STATE_FIELD, "name")
                    .relate(Label.FIELD_TYPE, "Name");

    final CodeGenerationParameter rankField =
            CodeGenerationParameter.of(Label.STATE_FIELD, "rank")
                    .relate(Label.FIELD_TYPE, "Rank")
                    .relate(Label.COLLECTION_TYPE, "List");

    final CodeGenerationParameter availableOnField =
            CodeGenerationParameter.of(Label.STATE_FIELD, "availableOn")
                    .relate(Label.FIELD_TYPE, "LocalDate");

    final CodeGenerationParameter relatedAuthors =
            CodeGenerationParameter.of(Label.STATE_FIELD, "relatedAuthors")
                    .relate(Label.FIELD_TYPE, "String")
                    .relate(Label.COLLECTION_TYPE, "Set");

    final CodeGenerationParameter authorRegisteredEvent =
            CodeGenerationParameter.of(Label.DOMAIN_EVENT, "AuthorRegistered")
                    .relate(CodeGenerationParameter.of(Label.STATE_FIELD, "id"));

    final CodeGenerationParameter authorRankedEvent =
            CodeGenerationParameter.of(Label.DOMAIN_EVENT, "AuthorRanked")
                    .relate(CodeGenerationParameter.of(Label.STATE_FIELD, "id"))
                    .relate(rankField);

    final CodeGenerationParameter authorRelatedEvent =
            CodeGenerationParameter.of(Label.DOMAIN_EVENT, "AuthorRelated")
                    .relate(CodeGenerationParameter.of(Label.STATE_FIELD, "id"))
                    .relate(CodeGenerationParameter.of(Label.STATE_FIELD, "relatedAuthors")
                            .relate(Label.FIELD_TYPE, "String")
                            .relate(Label.COLLECTION_TYPE, "Set")
                            .relate(Label.ALIAS, "relatedAuthor")
                            .relate(Label.COLLECTION_MUTATION, "ADDITION"));

    final CodeGenerationParameter authorsRelatedEvent =
            CodeGenerationParameter.of(Label.DOMAIN_EVENT, "AuthorsRelated")
                    .relate(CodeGenerationParameter.of(Label.STATE_FIELD, "id"))
                    .relate(CodeGenerationParameter.of(Label.STATE_FIELD, "relatedAuthors")
                            .relate(Label.FIELD_TYPE, "String")
                            .relate(Label.COLLECTION_TYPE, "Set")
                            .relate(Label.ALIAS, "")
                            .relate(Label.COLLECTION_MUTATION, "MERGE"));

    final CodeGenerationParameter authorUnrelatedEvent =
            CodeGenerationParameter.of(Label.DOMAIN_EVENT, "AuthorUnrelated")
                    .relate(CodeGenerationParameter.of(Label.STATE_FIELD, "id"))
                    .relate(CodeGenerationParameter.of(Label.STATE_FIELD, "relatedAuthors")
                            .relate(Label.FIELD_TYPE, "String")
                            .relate(Label.COLLECTION_TYPE, "Set")
                            .relate(Label.ALIAS, "relatedAuthor")
                            .relate(Label.COLLECTION_MUTATION, "REMOVAL"));

    final CodeGenerationParameter factoryMethod =
            CodeGenerationParameter.of(Label.AGGREGATE_METHOD, "withName")
                    .relate(METHOD_PARAMETER, "name")
                    .relate(Label.FACTORY_METHOD, "true")
                    .relate(authorRegisteredEvent);

    final CodeGenerationParameter rankMethod =
            CodeGenerationParameter.of(Label.AGGREGATE_METHOD, "changeRank")
                    .relate(METHOD_PARAMETER, "rank")
                    .relate(authorRankedEvent);

    final CodeGenerationParameter relatedAuthorMethod =
            CodeGenerationParameter.of(Label.AGGREGATE_METHOD, "relateAuthor")
                    .relate(CodeGenerationParameter.of(METHOD_PARAMETER, "relatedAuthors")
                            .relate(Label.ALIAS, "relatedAuthor")
                            .relate(Label.COLLECTION_MUTATION, "ADDITION"))
                    .relate(authorRelatedEvent);

    final CodeGenerationParameter relatedAuthorsMethod =
            CodeGenerationParameter.of(Label.AGGREGATE_METHOD, "relateAuthors")
                    .relate(CodeGenerationParameter.of(METHOD_PARAMETER, "relatedAuthors")
                            .relate(Label.ALIAS, "")
                            .relate(Label.COLLECTION_MUTATION, "MERGE"))
                    .relate(authorsRelatedEvent);

    final CodeGenerationParameter relatedAuthorsReplacementMethod =
            CodeGenerationParameter.of(Label.AGGREGATE_METHOD, "replaceAllRelatedAuthors")
                    .relate(CodeGenerationParameter.of(METHOD_PARAMETER, "relatedAuthors")
                            .relate(Label.ALIAS, "")
                            .relate(Label.COLLECTION_MUTATION, "REPLACEMENT"))
                    .relate(authorsRelatedEvent);

    final CodeGenerationParameter relatedAuthorRemovalMethod =
            CodeGenerationParameter.of(Label.AGGREGATE_METHOD, "unrelateAuthor")
                    .relate(CodeGenerationParameter.of(METHOD_PARAMETER, "relatedAuthors")
                            .relate(Label.ALIAS, "relatedAuthor")
                            .relate(Label.COLLECTION_MUTATION, "REMOVAL"))
                    .relate(authorUnrelatedEvent);

    final CodeGenerationParameter hideMethod =
            CodeGenerationParameter.of(Label.AGGREGATE_METHOD, "hide");

    return CodeGenerationParameter.of(Label.AGGREGATE, "Author")
            .relate(idField).relate(nameField).relate(rankField).relate(relatedAuthors)
            .relate(availableOnField).relate(factoryMethod).relate(rankMethod).relate(hideMethod)
            .relate(relatedAuthorMethod).relate(relatedAuthorsMethod).relate(relatedAuthorRemovalMethod)
            .relate(relatedAuthorsReplacementMethod).relate(authorRegisteredEvent).relate(authorRankedEvent)
            .relate(authorRelatedEvent).relate(authorsRelatedEvent).relate(authorUnrelatedEvent);
  }

  private CodeGenerationParameter nameValueObject() {
    return CodeGenerationParameter.of(Label.VALUE_OBJECT, "Name")
            .relate(CodeGenerationParameter.of(Label.VALUE_OBJECT_FIELD, "firstName")
                    .relate(Label.FIELD_TYPE, "String"))
            .relate(CodeGenerationParameter.of(Label.VALUE_OBJECT_FIELD, "lastName")
                    .relate(Label.FIELD_TYPE, "String"));
  }

  private CodeGenerationParameter rankValueObject() {
    return CodeGenerationParameter.of(Label.VALUE_OBJECT, "Rank")
            .relate(CodeGenerationParameter.of(Label.VALUE_OBJECT_FIELD, "points")
                    .relate(Label.FIELD_TYPE, "int"))
            .relate(CodeGenerationParameter.of(Label.VALUE_OBJECT_FIELD, "classification")
                    .relate(Label.FIELD_TYPE, "String").relate(Label.COLLECTION_TYPE, "Set"));
  }

  private Content[] contents() {
    return new Content[]{
            Content.with(JavaTemplateStandard.VALUE_OBJECT, new OutputFile(Paths.get(MODEL_PACKAGE_PATH).toString(), "Rank.java"), null, null, RANK_VALUE_OBJECT_CONTENT_TEXT),
            Content.with(JavaTemplateStandard.VALUE_OBJECT, new OutputFile(Paths.get(MODEL_PACKAGE_PATH, "author").toString(), "Name.java"), null, null, NAME_VALUE_OBJECT_CONTENT_TEXT)
    };
  }

  private static final String PROJECT_PATH =
          OperatingSystem.detect().isWindows() ?
                  Paths.get("D:\\projects", "xoom-app").toString() :
                  Paths.get("/home", "xoom-app").toString();

  private static final String MODEL_PACKAGE_PATH =
          Paths.get(PROJECT_PATH, "src", "main", "java",
                  "io", "vlingo", "xoomapp", "model").toString();

  private static final String NAME_VALUE_OBJECT_CONTENT_TEXT =
          "package io.vlingo.xoomapp.model; \\n" +
                  "public class Name { \\n" +
                  "... \\n" +
                  "}";

  private static final String RANK_VALUE_OBJECT_CONTENT_TEXT =
          "package io.vlingo.xoomapp.model; \\n" +
                  "public class Rank { \\n" +
                  "... \\n" +
                  "}";

}
