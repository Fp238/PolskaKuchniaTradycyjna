package com.example.polskakuchniatradycyjna.model


data class PersonOrder(
    val zupa: String,
    val dodatkiDoZupy: List<String> = emptyList(),
    val drugieDanie: String,
    val dodatkiDoDrugiegoDania: List<String> = emptyList(),
    val napoj: String,
    val typNapoju: String = "Brak"
) {
    fun getTotalPrice(): Double {
        var total = 0.0

        total += when (zupa) {
            "Rosół" -> 10.0
            "Pomidorowa" -> 12.0
            else -> 0.0
        }

        total += dodatkiDoZupy.size * 2.0

        total += when (drugieDanie) {
            "Schabowy" -> 20.0
            "Pieczony kurczak" -> 22.0
            else -> 0.0
        }

        total += dodatkiDoDrugiegoDania.size * 3.0

        total += when (napoj) {
            "Kompot" -> 5.0
            "Woda" -> 3.0
            "Sok" -> 6.0
            else -> 0.0
        }

        return total
    }
}