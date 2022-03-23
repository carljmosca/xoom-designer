// Copyright © 2012-2021 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.
package io.vlingo.xoom.designer.codegen.java.unittest.queries;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PreliminaryStatement {

  private static final String STATE_STORE_LOAD_PATTERN = "stateStore.write(\"%s\", %s, 1, NOOP_WRITER);";

  public static List<String> with(final String testMethodName) {
    return IntStream.range(1, TestCase.TEST_DATA_SET_SIZE + 1).mapToObj(dataIndex -> {
      final String testDataVariableName = TestDataFormatter.formatStaticVariableName(dataIndex, testMethodName);
      return String.format(STATE_STORE_LOAD_PATTERN, dataIndex, testDataVariableName);
    }).collect(Collectors.toList());
  }

  public static Collection<String> with(String testMethodName, List<String> compositeIdFields) {
    return IntStream.range(1, TestCase.TEST_DATA_SET_SIZE + 1).mapToObj(dataIndex -> {
      final String testDataVariableName = TestDataFormatter.formatStaticVariableName(dataIndex, testMethodName);
      return String.format(STATE_STORE_LOAD_PATTERN, compositeIdFields.stream().map(id -> "" + dataIndex).collect(Collectors.joining(":")) + ":" + dataIndex, testDataVariableName);
    }).collect(Collectors.toList());  }
}
