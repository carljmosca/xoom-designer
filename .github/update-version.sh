#!/bin/sh

VERSION=${1}

[[ "$VERSION" != "" ]] || (echo "The version has to be passed as the first argument" && exit)

mvn versions:set -DnewVersion=$VERSION
mvn versions:use-dep-version -Dincludes=io.vlingo.xoom -DdepVersion=$VERSION -DforceVersion=true

sed -i'.bkp' -E 's/(image: vlingo\/xoom-designer:)[1-9]*\.[0-9]*\.[0-9]*/\1'$VERSION'/g' docker-compose.yml
sed -i'.bkp' -E 's/(image: vlingo\/xoom-schemata:)[1-9]*\.[0-9]*\.[0-9]*/\1'$VERSION'/g' docker-compose.yml
sed -i'.bkp' -E 's/assertEquals\("[1-9]*\.[0-9]*\.[0-9]*(-SNAPSHOT)?"/assertEquals("'$VERSION'"/g' src/test/java/io/vlingo/xoom/designer/infrastructure/restapi/data/TaskExecutionContextMapperTest.java
sed -i'.bkp' -E 's/XOOM_VERSION_PLACEHOLDER = "[1-9]*\.[0-9]*\.[0-9]*(-SNAPSHOT)?"/XOOM_VERSION_PLACEHOLDER = "'$VERSION'"/g' src/main/java/io/vlingo/xoom/designer/Configuration.java

git add \
  pom.xml \
  docker-compose.yml \
  src/test/java/io/vlingo/xoom/designer/infrastructure/restapi/data/TaskExecutionContextMapperTest.java \
  src/main/java/io/vlingo/xoom/designer/Configuration.java

