@echo off
set MAVEN_WRAPPER_DIR=.mvn\wrapper
if defined JAVA_HOME (
  set JAVA_CMD=%JAVA_HOME%\bin\java
) else (
  set JAVA_CMD=java
)
%JAVA_CMD% -jar "%~dp0%MAVEN_WRAPPER_DIR%\maven-wrapper.jar" %*
