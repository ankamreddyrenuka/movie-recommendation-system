#!/usr/bin/env bash
MAVEN_WRAPPER_DIR=".mvn/wrapper"
JAVA_CMD=${JAVA_HOME:+"$JAVA_HOME/bin/java"}
if [ -z "$JAVA_CMD" ]; then
  JAVA_CMD=java
fi
DIRNAME=$(dirname "$0")
exec "$JAVA_CMD" -jar "$DIRNAME/$MAVEN_WRAPPER_DIR/maven-wrapper.jar" "$@"
