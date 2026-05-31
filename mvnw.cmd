@echo off
setlocal
set MAVEN_WRAPPER_DIR=.mvn\wrapper
set MAVEN_PROJECTBASEDIR=%~dp0
if defined JAVA_HOME (
  set "JAVA_CMD=%JAVA_HOME%\bin\java"
) else (
  set "JAVA_CMD=java"
)
"%JAVA_CMD%" -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTBASEDIR%" -cp "%MAVEN_PROJECTBASEDIR%%MAVEN_WRAPPER_DIR%\maven-wrapper.jar" org.apache.maven.wrapper.MavenWrapperMain %*
