package dataclass

/**
 * Data class reprezentująca smartfon.
 * Jest to niezmienny (immutable) nośnik danych, który automatycznie
 * dostarcza implementacje equals(), hashCode(), toString(), copy().
 * Zastępuje klasę Smartfon, eliminując potrzebę ręcznego pisania tych metod.
 * Wszystkie właściwości są niemutowalne (val)
 */
data class Smartfon(
    val producent: String,
    val model: String,
    val rokProdukcji: Int,
    val systemOperacyjny: String,
    val ilośćPamięci: Int
) : Urzadzenie