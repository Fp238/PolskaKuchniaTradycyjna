package com.example.polskakuchniatradycyjna.model


data class PersonOrder(
    val zupa: String,
    val dodatkiDoZupy: List<String> = emptyList(),
    val drugieDanie: String,
    val dodatkiDoDrugiegoDania: List<String> = emptyList(),
    val napoj: String,
    val typNapoju: String = "Brak"
) {
    fun getTotalPrice(): Int {
        var total = 0

        total += when (zupa) {
            "Rosół" -> 10
            "Pomidorowa" -> 12
            else -> 0
        }

        total += dodatkiDoZupy.size * 2

        total += when (drugieDanie) {
            "Schabowy" -> 20
            "Pieczony kurczak" -> 22
            else -> 0
        }

        total += dodatkiDoDrugiegoDania.size * 3

        total += when (napoj) {
            "Kompot" -> 5
            "Woda" -> 3
            "Sok" -> 6
            else -> 0
        }

        return total
    }
}