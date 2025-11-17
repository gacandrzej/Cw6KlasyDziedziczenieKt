#!/bin/bash
# run_all_tests.sh
# Skrypt do kompilacji projektu Kotlin z automatycznym pobieraniem

set -e

# Katalogi
BIN_DIR="bin"
LIB_DIR="libs"
REPORT_DIR="reports"
SCRIPTS_DIR="$APPDATA/Python/Python311/Scripts"

# Wersje
KOTLIN_VERSION="2.1.0"
JUNIT_VERSION="5.10.0"

# Ścieżka do lokalnego kompilatora Kotlin
KOTLIN_HOME="$LIB_DIR/kotlin-compiler-$KOTLIN_VERSION"

# Tworzymy katalogi, jeśli nie istnieją
mkdir -p $BIN_DIR $LIB_DIR $REPORT_DIR

# Sprawdź czy kompilator Kotlin istnieje, jeśli nie - pobierz
if [ ! -d "$KOTLIN_HOME" ]; then
    echo "📥 Pobieram kompilator Kotlin $KOTLIN_VERSION..."
    KOTLIN_ZIP="$LIB_DIR/kotlin-compiler-$KOTLIN_VERSION.zip"

    curl -L -o "$KOTLIN_ZIP" \
        "https://github.com/JetBrains/kotlin/releases/download/v$KOTLIN_VERSION/kotlin-compiler-$KOTLIN_VERSION.zip"

    echo "📦 Wypakowuję kompilator..."
    unzip -q "$KOTLIN_ZIP" -d "$LIB_DIR"
    mv "$LIB_DIR/kotlinc" "$KOTLIN_HOME"
    rm "$KOTLIN_ZIP"

    echo "✅ Kompilator Kotlin gotowy"
fi

echo "✅ Używam kompilatora Kotlin z: $KOTLIN_HOME"

# Pobranie JUnit (jeśli nie ma)
JUNIT_CONSOLE="$LIB_DIR/junit-platform-console-standalone-$JUNIT_VERSION.jar"

if [ ! -f "$JUNIT_CONSOLE" ]; then
    echo "📥 Pobieram JUnit..."
    curl -L -o "$JUNIT_CONSOLE" \
        "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/$JUNIT_VERSION/junit-platform-console-standalone-$JUNIT_VERSION.jar"
fi

# Sprawdź czy plik JAR jest poprawny
if ! jar -tf "$JUNIT_CONSOLE" > /dev/null 2>&1; then
    echo "🔄 Plik JUnit uszkodzony, pobieram ponownie..."
    rm "$JUNIT_CONSOLE"
    curl -L -o "$JUNIT_CONSOLE" \
        "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/$JUNIT_VERSION/junit-platform-console-standalone-$JUNIT_VERSION.jar"
fi

# Instalacja junit2html
echo "🔍 Sprawdzam junit2html..."
pip install junit2html

# Kompilacja kodu źródłowego i testów Kotlin
echo "🔨 Kompilacja kodu Kotlin..."

# Znajdź wszystkie pliki .kt
KOTLIN_FILES=$(find src test -name "*.kt" 2>/dev/null | tr '\n' ' ')

if [ -z "$KOTLIN_FILES" ]; then
    echo "❌ Nie znaleziono plików .kt do kompilacji"
    exit 1
fi

echo "📄 Znaleziono $(echo $KOTLIN_FILES | wc -w) plików Kotlin"

# Ustaw separator ścieżki
if [[ "$OS" == "Windows_NT" ]]; then
  CP_SEP=";"
  KOTLINC_CMD="$KOTLIN_HOME/bin/kotlinc.bat"
else
  CP_SEP=":"
  KOTLINC_CMD="$KOTLIN_HOME/bin/kotlinc"
fi

# Kompilacja
echo "⚡ Kompiluję pliki Kotlin..."
"$KOTLINC_CMD" $KOTLIN_FILES \
    -cp "$JUNIT_CONSOLE" \
    -d $BIN_DIR \
    -Xskip-metadata-version-check

# Sprawdź czy kompilacja się udała
if [ $? -ne 0 ]; then
    echo "❌ Błąd kompilacji Kotlin"
    exit 1
fi

echo "✅ Kompilacja zakończona sukcesem"

# Uruchamianie testów
echo "🚀 Uruchamianie testów..."
java -cp "$BIN_DIR$CP_SEP$JUNIT_CONSOLE$CP_SEP$KOTLIN_HOME/lib/kotlin-stdlib.jar" \
    org.junit.platform.console.ConsoleLauncher \
    --scan-classpath \
    --reports-dir $REPORT_DIR \
    --details verbose

echo "✅ Testy zakończone. Raport w katalogu $REPORT_DIR"

# Konwersja XML -> HTML
echo "📊 Konwersja raportu..."
if [[ "$OS" == "Windows_NT" ]]; then
  "$SCRIPTS_DIR/junit2html.exe" "$REPORT_DIR/TEST-junit-jupiter.xml" "$REPORT_DIR/report-jupiter.html" 2>/dev/null || echo "⚠️  Nie udało się przekonwertować raportu"
else
  junit2html "$REPORT_DIR/TEST-junit-jupiter.xml" "$REPORT_DIR/report-jupiter.html" 2>/dev/null || echo "⚠️  Nie udało się przekonwertować raportu"
fi

echo "🎉 Wszystko gotowe!"