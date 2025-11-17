# Klasy, dziedziczenie, polimorfizm w Kotlinie

![Build Status](https://github.com/gacandrzej/Cw6JavaKlasyDziedziczenie/actions/workflows/build.yml/badge.svg)

Projekt edukacyjny w Javie prezentujący koncepcje programowania obiektowego:
- **Klasy zapieczętowane (Sealed Classes)** - kontrola hierarchii dziedziczenia
- **Data class** - niezmienne klasy danych z automatycznymi metodami
- **Pattern Matching** - nowoczesne przetwarzanie typów z instanceof i switch
- **Polimorfizm** - dynamiczne wiązanie metod w hierarchii klas

Projekt zawiera również testy jednostkowe JUnit 5,
które weryfikują poprawność działania algorytmów
oraz metod.

---

## 📌 Spis treści
1. [Opis projektu](#opis-projektu)
2. [Technologie](#technologie)
3. [Instalacja](#instalacja)
4. [Użycie](#użycie)
5. [Przykład kodu](#przykład-kodu)
6. [Testy jednostkowe](#testy-jednostkowe)
7. [Diagram sekwencji](#diagram-sekwencji)
8. [Zrzuty ekranu](#zrzuty-ekranu)
9. [Uruchamianie testów](#uruchamianie-testów)
10. [Autor](#autor)
11. [Licencja](#licencja)


---

## 📝Opis projektu
Projekt ma na celu:
- Poznanie nowoczesnych funkcji Javy: sealed classes, records, pattern matching
- Wykorzystanie polimorfizmu oraz testów jednostkowych w JUnit 5
- Ćwiczenie pracy z hierarchią klas i interfejsów
- Demonstrację bezpiecznego pattern matching z sealed classes

### Główne koncepcje:
- **Sealed Classes**: Kontrola dziedziczenia przez `permits`
- **Data class**: Automatyczne generowanie equals, hashCode, toString oraz copy()
- **Pattern Matching**: Bezpieczne rzutowanie i dekonstrukcja typów
- **Polimorfizm**: Dynamiczne wywoływanie metod w hierarchii

---

## ⚙️Technologie
- **Java 21+** (wymagane dla record patterns i sealed classes)
- **JUnit 5** (testy jednostkowe)
- **Git** (kontrola wersji)

---

## 💻Instalacja
```bash
# Sklonuj repozytorium
git clone https://github.com/gacandrzej/Cw6JavaKlasyDziedziczenie.git

# Przejdź do katalogu projektu
cd Cw6JavaKlasyDziedziczenie

# Kompilacja
javac -d bin src/**/*.java

# Uruchomienie 
java -cp bin rekord.TestRecord 
java -cp bin sealed.TestSealed
java -cp bin komputery.TestKomputerow
```

---

## 🚀Użycie

Projekt zawiera trzy główne moduły:

1. Recordy (rekord/)
- Demonstracja recordów jako niezmiennych klas danych
- Pattern matching z dekonstrukcją recordów
- Metody copy i walidacja w recordach
```bash
  java -cp bin rekord.TestRecord 
```
2. Sealed Classes (sealed/)
- Hierarchia zapieczętowanych klas
- Bezpieczny pattern matching w switch expressions
- Kontrola dziedziczenia przez permits
```bash
  java -cp bin sealed.TestSealed 
```
3. Klasy dziedziczące. Polimorfizm (komputery/)
- Klasyczna hierarchia dziedziczenia
- Przesłanianie metod (@Override)
- Dynamiczne wiązanie metod
```bash
  java -cp bin komputery.TestKomputerow
```

---

## 📌Przykład kodu
```kotlin
data class Komputer(
    val producent: String,
    val model: String,
    val rokProdukcji: Int
) : Urzadzenie {
    fun wlacz() {
        println("Komputer się uruchamia ...")
    }
}
```

---

## 🧪Testy jednostkowe

```java
 package sealed;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmartfonTest {

    private Smartfon smartfon;

    @BeforeEach
    void setUp() {
        smartfon = new Smartfon("Samsung", "Galaxy S21", 2022, "Android", 128);
    }

    @AfterEach
    void tearDown() {
        smartfon = null;
    }

    @Test
    void getProducent() {
        assertEquals("Samsung", smartfon.getProducent());
    }

    @Test
    void getModel() {
        assertEquals("Galaxy S21", smartfon.getModel());
    }

    @Test
    void getRokProdukcji() {
        assertEquals(2022, smartfon.getRokProdukcji());
    }

    @Test
    void getSystemOperacyjny() {
        assertEquals("Android", smartfon.getSystemOperacyjny());
    }

    @Test
    void getIlośćPamięci() {
        assertEquals(128, smartfon.getIlośćPamięci());
    }

    @Test
    void testToString() {
        // Zakładając, że toString() w klasie bazowej Komputer jest poprawnie zaimplementowane
        // i Smartfon.toString() je rozszerza.
        String expected = "Komputer{producent='Samsung', model='Galaxy S21', rokProdukcji=2022}Smartfon{systemOperacyjny='Android', ilośćPamięci=128}";
        assertEquals(expected, smartfon.toString());
    }

    @Test
    void włacz() {
        // Test metody z efektem ubocznym (wydruk na konsolę)
        // W bardziej zaawansowanych scenariuszach można by przechwycić strumień wyjścia.
        // Tutaj po prostu sprawdzamy, czy metoda się wykona bez błędu.
        smartfon.włacz();
    }

    @Test
    void testEqualsAndHashCode() {
        Smartfon smartfon2 = new Smartfon("Samsung", "Galaxy S21", 2022, "Android", 128);
        assertEquals(smartfon, smartfon2, "Dwa identyczne smartfony powinny być równe.");
        assertEquals(smartfon.hashCode(), smartfon2.hashCode(), "HashCode dla równych obiektów powinien być taki sam.");

        Smartfon smartfon3 = new Smartfon("Apple", "iPhone 13", 2021, "iOS", 256);
        assertNotEquals(smartfon, smartfon3, "Dwa różne smartfony nie powinny być równe.");
    }
}
```
Uruchamianie:
```bash
# Uruchomienie testów w terminalu
  javac -cp junit-platform-console-standalone-1.10.0.jar -d bin test/**/*.java
  java -jar junit-platform-console-standalone-1.10.0.jar --class-path bin --scan-class-path
```

---

## 📊Diagram sekwencji

```mermaid
classDiagram
    direction TB

%% =============================================
%% SEALED CLASSES HIERARCHY (sealed package)
%% =============================================
    note for Komputer "sealed class\npermits Laptop, Smartfon"
    class Komputer {
        <<sealed>>
        -String producent
        -String model
        -int rokProdukcji
        +Komputer(String, String, int)
        +getProducent() String
        +getModel() String
        +getRokProdukcji() int
        +włacz() void
        +toString() String
        +equals(Object) boolean
        +hashCode() int
    }

    class Laptop {
        -double waga
        -int iloscPortowUSB
        -float czasPracyNaBaterii
        +Laptop(String, String, int, double, int, float)
        +getWaga() double
        +getIloscPortowUSB() int
        +getCzasPracyNaBaterii() float
        +włacz() void
        +toString() String
        +equals(Object) boolean
        +hashCode() int
    }

    class Smartfon {
        -String systemOperacyjny
        -int ilośćPamięci
        +Smartfon(String, String, int, String, int)
        +getSystemOperacyjny() String
        +getIlośćPamięci() int
        +włacz() void
        +toString() String
    }

    Komputer <|-- Laptop : extends
    Komputer <|-- Smartfon : extends

%% =============================================
%% RECORDS (rekord package)
%% =============================================
    class KomputerRecord {
        <<record>>
        +String producent
        +String model
        +int rokProdukcji
        +wlacz() void
        +copy(String, String, int) KomputerRecord
        +producent() String
        +model() String
        +rokProdukcji() int
        +toString() String
        +equals(Object) boolean
        +hashCode() int
    }

    class LaptopRecord {
        <<record>>
        +String producent
        +String model
        +int rokProdukcji
        +double waga
        +int iloscPortowUSB
        +float czasPracyNaBaterii
        +wlacz() void
        +czyLekki() boolean
    }

    class SmartfonRecord {
        <<record>>
        +String producent
        +String model
        +int rokProdukcji
        +String systemOperacyjny
        +int ilośćPamięci
        +wlacz() void
        +czyDuzaPamiec() boolean
    }

%% =============================================
%% PATTERN MATCHING USAGE
%% =============================================
    class RecordTester {
        <<utility>>
        +opisUrzadzenia(Object) String
        +ocenUrzadzenie(Object) String
        +przetworzKolekcje(Object[]) void
    }

    class TestRecord {
        +main() void
        +testNiezalezneRekordy() void
    }

    class TestSealed {
        +main() void
        +przetworzKomputer(Komputer) void
    }

%% =============================================
%% RELATIONSHIPS
%% =============================================
    RecordTester ..> KomputerRecord : uses
    RecordTester ..> LaptopRecord : uses
    RecordTester ..> SmartfonRecord : uses

    TestRecord ..> KomputerRecord : tests
    TestRecord ..> LaptopRecord : tests
    TestRecord ..> SmartfonRecord : tests

    TestSealed ..> Komputer : tests
    TestSealed ..> Laptop : tests
    TestSealed ..> Smartfon : tests

%% =============================================
%% PATTERN MATCHING EXAMPLES
%% =============================================
    note for RecordTester "Pattern Matching:\nswitch (urzadzenie) {\n  case KomputerRecord(String p, String m, int r)\n  case LaptopRecord(String p, String m, int r, double w, ...)\n}"

    note for TestSealed "Safe Pattern Matching:\nswitch (komputer) {\n  case Laptop l -> ...\n  case Smartfon s -> ...\n  // No default needed!\n}"
```
---

## 🖼️Zrzuty ekranu
![img_2.png](img_2.png)

---

## 🏃Uruchamianie testów

Projekt zawiera skrypt `run_all_tests.sh`, który umożliwia:

- pobranie potrzebnych bibliotek JUnit 5,
- pobranie i zainstalowanie `junit2html`,
- kompilację kodu źródłowego i testów jednostkowych,
- uruchomienie testów i wygenerowanie raportów w formacie XML oraz HTML.

### Co robi skrypt

- Tworzy katalogi `bin/`, `libs/` i `reports/` (jeśli nie istnieją).
- Pobiera pliki JUnit (`junit-jupiter-api` i `junit-platform-console-standalone`) do `libs/`.
- Pobiera i instaluje `junit2html` (jeśli nie jest zainstalowany).
- Kompiluje kod źródłowy i testy do katalogu `bin/`.
- Uruchamia wszystkie testy jednostkowe i zapisuje wyniki w XML w katalogu `reports/`.
- Konwertuje raporty XML na HTML przy użyciu `junit2html`.

Pliki HTML powstają w katalogu `reports/` z nazwami:

- `report-jupiter.html`
- `report-platform.html`
- `report-vintage.html`

### Przykład raportu

Po uruchomieniu skryptu w katalogu `reports/` znajdziesz pliki HTML, które można otworzyć w przeglądarce, aby zobaczyć wyniki testów w czytelnej formie.

### Uruchomienie

W terminalu (Bash / Git Bash / Linux / Mac):

```bash
  ./run_all_tests.sh
```

---

## 👤Autor
- [GitHub: gacandrzej](https://github.com/gacandrzej)


- Email: [gacandrzej@gmail.com](mailto:gacandrzej@gmail.com)

---

## Licencja
- MIT License © 2025 Gac Andrzej