package sealed

import komputery.Komputer
import komputery.Laptop
import komputery.Smartfon

fun main() {
    val test = TestSealed().testAll()
}

class TestSealed {
    fun testAll() {
        val komputer = Komputer("Asus", "AX25", 2024)
        // komputer.model = "sdfsd" // właściwość public, ale można zmienić tylko jeśli jest 'var'

        val pc2 = Komputer()
        // settery - bezpośredni dostęp do właściwości
        pc2.producent = "HP"
        pc2.model = "QW23"
        pc2.rokProdukcji = 2025

        // gettery - bezpośredni dostęp do właściwości
        println(komputer.toString())
        println(pc2.toString())

        // test klasy Laptop
        val laptop = Laptop("Lenovo", "GPX2", 2023, 1.6, 3, 6.5f) // Ctrl + P
        println(laptop.toString())

        // z konstruktorem bezargumentowym
        val laptop2 = Laptop()
        laptop2.producent = "Samsung"
        laptop2.model = "XCV"
        laptop2.rokProdukcji = 2026
        laptop2.waga = 2.2
        laptop2.iloscPortowUSB = 4
        laptop2.czasPracyNaBaterii = 10.3f

        // wypisanie, korzystamy z metody toString()
        println(laptop2.toString())

        // testujemy polimorfizm
        komputer.wlacz()
        pc2.wlacz()
        println(System.lineSeparator())
        laptop.wlacz()
        laptop2.wlacz()

        // Smartfon
        val smartfon = Smartfon("Google", "Pixel 10 Pro", 2025, "Android", 8)
        val smartfon2 = Smartfon("Apple", "17 Pro", 2025, "Apple IOS", 16)
        smartfon2.producent = "Apple"
        smartfon2.model = "17 Pro"
        smartfon2.rokProdukcji = 2025
        smartfon2.systemOperacyjny = "ios 17"
        smartfon2.iloscPamieci = 16

        println(smartfon.toString())
        println(smartfon2.toString())
        smartfon.wlacz()
        smartfon2.wlacz()
    }

}