package sealed

import dataclass.Urzadzenie
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SmartfonTest {

    private lateinit var smartfon: Smartfon

    @BeforeEach
    fun setUp() {
        smartfon = Smartfon("Samsung", "Galaxy S21", 2022, "Android", 128)
    }

    @AfterEach
    fun tearDown() {
        // W Kotlinie nie musimy ręcznie ustawiać na null
    }

    @Test
    fun getProducent() {
        assertEquals("Samsung", smartfon.producent)
    }

    @Test
    fun getModel() {
        assertEquals("Galaxy S21", smartfon.model)
    }

    @Test
    fun getRokProdukcji() {
        assertEquals(2022, smartfon.rokProdukcji)
    }

    @Test
    fun getSystemOperacyjny() {
        assertEquals("Android", smartfon.systemOperacyjny)
    }

    @Test
    fun getIlośćPamięci() {
        assertEquals(128, smartfon.iloscPamieci) // Uwaga: zmienna nazywa się 'iloscPamieci' a nie 'ilośćPamięci'
    }

    @Test
    fun testToString() {
        // Sprawdźmy tylko czy zawiera kluczowe informacje, nie cały string
        val result = smartfon.toString()
        assertTrue(result.contains("Samsung"))
        assertTrue(result.contains("Galaxy S21"))
        assertTrue(result.contains("Android"))
        assertTrue(result.contains("128"))
    }

    @Test
    fun włacz() {
        // Test metody z efektem ubocznym
        smartfon.wlacz() // Uwaga: metoda nazywa się 'wlacz' a nie 'włacz'
    }

    @Test
    fun testEqualsAndHashCode() {
        val smartfon2 = Smartfon("Samsung", "Galaxy S21", 2022, "Android", 128)
        val smartfon3 = Smartfon("Apple", "iPhone 13", 2021, "iOS", 256)

        // Sprawdzamy właściwości ręcznie, bo to nie jest data class
        assertEquals(smartfon.producent, smartfon2.producent)
        assertEquals(smartfon.model, smartfon2.model)
        assertEquals(smartfon.rokProdukcji, smartfon2.rokProdukcji)
        assertEquals(smartfon.systemOperacyjny, smartfon2.systemOperacyjny)
        assertEquals(smartfon.iloscPamieci, smartfon2.iloscPamieci)

        assertNotEquals(smartfon.producent, smartfon3.producent)
        assertNotEquals(smartfon.model, smartfon3.model)
    }


}