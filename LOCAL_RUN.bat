@echo off
setlocal

REM Launch TravelDNA locally without relying on Maven Wrapper
REM Requires: Java 21 installed and MAVEN_HOME (or mvn in PATH) if building is needed.

REM If target jar exists, run it. Otherwise, try to build with mvn.

set JAR=target\app.jar

if exist %JAR% (
  echo Running %JAR%...
) else (
  echo Building jar with mvn...
  mvn -q -DskipTests clean package
  REM Copy the built jar to app.jar for consistent execution name
  for /f %%f in ('dir /b /s target\*.jar ^| findstr -v sources ^| findstr -v javadoc ^| findstr -v original') do (
    copy /Y "%%f" "%JAR%" >nul
    goto runit
  )
  :runit
)

if not exist %JAR% (
  echo Failed to find or create %JAR%.
  exit /b 1
)

echo Starting server on http://localhost:8081 ...
java -jar %JAR%
pause


