#!/usr/bin/env bash
MAVEN_WRAPPER_DIR=".mvn/wrapper"
JAVA_CMD=${JAVA_HOME:+"$JAVA_HOME/bin/java"}
if [ -z "$JAVA_CMD" ]; then
  JAVA_CMD=java
fi
DIRNAME=$(dirname "$0")
exec "$JAVA_CMD" -Dmaven.multiModuleProjectDirectory="$DIRNAME" -cp "$DIRNAME/$MAVEN_WRAPPER_DIR/maven-wrapper.jar" org.apache.maven.wrapper.MavenWrapperMain "$@"
