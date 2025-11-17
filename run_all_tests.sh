#!/bin/bash
# run_all_tests.sh
# Kompletny skrypt do pobrania JUnit,
# kompilacji projektu
# i uruchomienia testów z raportem

set -e

# Katalogi
BIN_DIR="bin"
LIB_DIR="libs"
REPORT_DIR="reports"
Scripts_DIR="$APPDATA/Python/Python311/Scripts"

# Wersje JUnit
JUNIT_VERSION="5.10.0"
PLATFORM_VERSION="1.10.0"

# Tworzymy katalogi, jeśli nie istnieją
mkdir -p $BIN_DIR $LIB_DIR $REPORT_DIR

# Pobranie JUnit (jeśli nie ma)
JUNIT_API="$LIB_DIR/junit-jupiter-api-$JUNIT_VERSION.jar"
JUNIT_CONSOLE="$LIB_DIR/junit-platform-console-standalone-$PLATFORM_VERSION.jar"

if [ ! -f "$JUNIT_API" ]; then
    echo "Pobieram junit-jupiter-api..."
    curl -L -o "$JUNIT_API" "https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-api/$JUNIT_VERSION/junit-jupiter-api-$JUNIT_VERSION.jar"
fi

if [ ! -f "$JUNIT_CONSOLE" ]; then
    echo "Pobieram junit-platform-console-standalone..."
    curl -L -o "$JUNIT_CONSOLE" "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/$PLATFORM_VERSION/junit-platform-console-standalone-$PLATFORM_VERSION.jar"
fi

# Instalacja junit2html (jeśli nie ma)
echo "Instaluję junit2html..."
pip install junit2html

# Kompilacja kodu źródłowego i testów
echo "Kompilacja kodu..."
if [[ "$OS" == "Windows_NT" ]]; then
  CP_SEP=";"
else
  CP_SEP=":"
fi

javac -d $BIN_DIR -cp "$JUNIT_API$CP_SEP$JUNIT_CONSOLE" src/**/*.java test/**/*.java

# Uruchamianie testów
echo "Uruchamianie testów..."
java -jar "$JUNIT_CONSOLE" \
    --classpath $BIN_DIR \
    --scan-classpath \
    --reports-dir $REPORT_DIR \
    --details verbose

echo "✅ Testy zakończone. Raport w katalogu $REPORT_DIR"

echo "Konwersja XML -> HTML"

if [[ "$OS" == "Windows_NT" ]]; then
  "$Scripts_DIR/junit2html.exe" "$REPORT_DIR/TEST-junit-jupiter.xml" "$REPORT_DIR/report-jupiter.html"
else
  junit2html "$REPORT_DIR/TEST-junit-jupiter.xml" "$REPORT_DIR/report-jupiter.html"
fi



