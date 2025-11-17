package dataclass

/**
 * Rekord reprezentujący laptopa.
 * Zastępuje klasę Laptop, dostarczając zwięzłą, niezmienną reprezentację danych.
 */
data class Laptop(
    val producent: String,
    val model: String,
    val rokProdukcji: Int,
    val waga: Double,
    val iloscPortowUSB: Int,
    val czasPracyNaBaterii: Float
) : Urzadzenie