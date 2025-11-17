package dataclass

// Sealed interface - kompilator wie jakie są wszystkie podtypy
/**
 * W Kotlinie sealed interface nie wymaga jawnego wymieniania podtypów
 * przez permits - kompilator automatycznie rozpoznaje wszystkie
 * klasy/obiekty w tym samym pliku lub kompilacji, które implementują
 * ten interface
 */
sealed interface Urzadzenie {
}