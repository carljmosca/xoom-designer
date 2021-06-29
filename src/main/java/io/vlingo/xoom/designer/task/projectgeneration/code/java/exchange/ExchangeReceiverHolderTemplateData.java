// Copyright © 2012-2021 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.xoom.designer.task.projectgeneration.code.java.exchange;

import io.vlingo.xoom.actors.Definition;
import io.vlingo.xoom.codegen.content.Content;
import io.vlingo.xoom.codegen.content.ContentQuery;
import io.vlingo.xoom.codegen.parameter.CodeGenerationParameter;
import io.vlingo.xoom.codegen.template.TemplateData;
import io.vlingo.xoom.codegen.template.TemplateParameters;
import io.vlingo.xoom.codegen.template.TemplateStandard;
import io.vlingo.xoom.designer.task.projectgeneration.Label;
import io.vlingo.xoom.designer.task.projectgeneration.code.java.JavaTemplateStandard;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.vlingo.xoom.designer.task.projectgeneration.code.java.JavaTemplateStandard.AGGREGATE;
import static io.vlingo.xoom.designer.task.projectgeneration.code.java.JavaTemplateStandard.AGGREGATE_PROTOCOL;
import static io.vlingo.xoom.designer.task.projectgeneration.code.java.TemplateParameter.*;

public class ExchangeReceiverHolderTemplateData extends TemplateData {

  private final TemplateParameters parameters;

  public static List<TemplateData> from(final String exchangePackage,
                                        final Stream<CodeGenerationParameter> aggregates,
                                        final List<Content> contents) {
    return aggregates.flatMap(aggregate -> aggregate.retrieveAllRelated(Label.EXCHANGE))
            .filter(exchange -> exchange.retrieveRelatedValue(Label.ROLE, ExchangeRole::of).isConsumer())
            .map(exchange -> new ExchangeReceiverHolderTemplateData(exchangePackage, exchange, contents))
            .collect(Collectors.toList());
  }

  private ExchangeReceiverHolderTemplateData(final String exchangePackage,
                                             final CodeGenerationParameter exchange,
                                             final List<Content> contents) {
    final List<ExchangeReceiver> receiversParameters = ExchangeReceiver.from(exchange);
    this.parameters =
            TemplateParameters.with(PACKAGE_NAME, exchangePackage)
                    .and(EXCHANGE_RECEIVERS, receiversParameters)
                    .and(AGGREGATE_PROTOCOL_NAME, exchange.parent().value)
                    .andResolve(EXCHANGE_RECEIVER_HOLDER_NAME, params -> standard().resolveClassname(params))
                    .addImports(resolveImports(exchange, receiversParameters, contents));
  }

  private Set<String> resolveImports(final CodeGenerationParameter exchange,
                                     final List<ExchangeReceiver> receiversParameters,
                                     final List<Content> contents) {
    final Set<String> imports = new HashSet<>();
    final String aggregateName = exchange.parent().value;
    final boolean involvesActorLoad =
            receiversParameters.stream().anyMatch(receiver -> !receiver.dispatchToFactoryMethod);

    if (involvesActorLoad) {
      final String aggregateEntityName = AGGREGATE.resolveClassname(aggregateName);
      imports.add(ContentQuery.findFullyQualifiedClassName(AGGREGATE, aggregateEntityName, contents));
      imports.add(Definition.class.getCanonicalName());
    }

    imports.add(ContentQuery.findFullyQualifiedClassName(AGGREGATE_PROTOCOL, aggregateName, contents));
    imports.addAll(receiversParameters.stream().map(receiver -> receiver.localTypeQualifiedName).collect(Collectors.toSet()));
    return imports;
  }

  @Override
  public TemplateParameters parameters() {
    return parameters;
  }

  @Override
  public TemplateStandard standard() {
    return JavaTemplateStandard.EXCHANGE_RECEIVER_HOLDER;
  }

}
