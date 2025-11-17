package dataclass

import java.util.HashSet

fun main() {
    val test = TestDataClass().testAll()
}

class TestDataClass {

    fun testAll() {
        val k1 = Komputer("Dell", "XPS", 2022)
        val k2 = Komputer("Dell", "XPS", 2022)
        val k3 = Komputer("HP", "Pavilion", 2021)

        println("k1: $k1") // wywołanie toString()
        println("k2: $k2")
        println(k1 == k2) // w Kotlinie używamy == zamiast equals()
        println("k1: ${k1.hashCode()}")
        println("k2: ${k2.hashCode()}")

        // inne testy
        val k4 = Komputer("Lenovo", "HYP", 2025)
        val k5 = k4.copy(model = "Latitude", rokProdukcji = -1) // usunięty null dla producent

        println("k4: $k4")
        println("k5: $k5")

        // HashSet
        val set = HashSet<Komputer>()
        set.add(k4)
        set.add(k5)
        set.add(Komputer("Lenovo", "HYP", 2025)) // nie dodany, bo equals/hashCode takie same jak k4

        println("Zawartość HashSet:")
        for (k in set) {
            println(k)
        }

        println(opisUrzadzenia(k1))
    }

    fun opisUrzadzenia(urzadzenie: Urzadzenie): String {
        return when (urzadzenie) {
            is Komputer -> {
                // Bez destructuring - dostęp przez właściwości
                String.format("🖥️ Komputer %s %s (%d)", urzadzenie.producent, urzadzenie.model, urzadzenie.rokProdukcji)
            }
            is Laptop -> {
                String.format("💻 Laptop %s %s (%.1fkg, %dh)", urzadzenie.producent, urzadzenie.model, urzadzenie.waga, urzadzenie.czasPracyNaBaterii)
            }
            is Smartfon -> {
                String.format("📱 Smartfon %s %s (%s, %dGB)", urzadzenie.producent, urzadzenie.model, urzadzenie.systemOperacyjny, urzadzenie.ilośćPamięci)
            }
        }
    }
}