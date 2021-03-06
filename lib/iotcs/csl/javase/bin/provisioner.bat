@REM #
@REM # Copyright (c) 2016, Oracle and/or its affiliates.  All rights reserved.
@REM # 
@REM # This software is dual-licensed to you under the MIT License (MIT) and the
@REM # Universal Permissive License (UPL).  See the LICENSE file in the root
@REM # directory for license terms.  You may choose either license, or both.
@REM #
@REM
@REM # This script recognizes the following environment variables
@REM # JAVA_HOME - must be set in the environment if java.exe and keytool.exe
@REM #             are not in the path.

@echo off

@REM clear the error level in case it had been explicitly set in the environment
set ERRORLEVEL=

setlocal
setlocal ENABLEDELAYEDEXPANSION

set exitcode=0

set JAVA=

echo "WARNING: This tool has been deprecated. Please follow documentation to download provisioning file generated by the Oracle IoT Cloud Service."

@REM # Strip the quotes if they exist and then always
@REM # quote JAVA_HOME. The for loop should only
@REM # iterate once if JAVA_HOME is set.

set count=0
for /f "tokens=*" %%a in ("!JAVA_HOME!") do (
    set /a count=!count! + 1
    set JAVA_HOME=%%~a
)
if !count! EQU 0 (
    for %%X in (java.exe) do (set JAVA=%%~$PATH:X)
    if [!JAVA!]==[] (
        echo JAVA_HOME not set and no java.exe in PATH
        exit /b 1
    )

    set JAVA=java.exe
) else (
    if NOT exist !JAVA_HOME!\bin\java.exe (
        echo JAVA_HOME is set but !JAVA_HOME!\bin\java.exe does not exist.
        exit /b 1
    )

    set JAVA=!JAVA_HOME!\bin\java.exe
)

@REM # Quotes will be stripped by %~dp0.
@REM # Do not add trailing \, which will escape the
@REM # trailing quote when the string in quoted.

set SCRIPTHOME=%~dp0
set LIB_DIR=!SCRIPTHOME!\..\lib

set exitcode=0

if "%1"=="-h" (
@REM define a variable containing a single backspace character
    for /f %%A in ('"prompt $H &echo on &for %%B in (1) do rem"') do set BS=%%A
    echo.
    echo "usage:"
@REM echo without a new line and 4 leading spaces
    <nul (set/p temp=".!BS!    provisioner")
    "!JAVA!" -cp "!LIB_DIR!\device-library.jar" ^
      com.oracle.iot.client.impl.trust.DefaultTrustedAssetsProvisioner -h
    goto end
)

"!JAVA!" -cp "!LIB_DIR!\device-library.jar" ^
    com.oracle.iot.client.impl.trust.DefaultTrustedAssetsProvisioner %*

:end

exit /b !exitcode!
