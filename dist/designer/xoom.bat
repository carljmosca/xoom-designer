@ECHO OFF
set VLINGO_XOOM_STARTER_HOME=%~dp0
set directoryArg=--currentDirectory
set args=%* %directoryArg% %CD%
call java -jar %VLINGO_XOOM_STARTER_HOME%\bin\vlingo-xoom-starter-dist.jar %args%
