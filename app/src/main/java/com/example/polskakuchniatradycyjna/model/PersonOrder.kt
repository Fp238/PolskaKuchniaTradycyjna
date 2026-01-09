package com.example.polskakuchniatradycyjna.model


data class PersonOrder(
    val zupa: String,
    val zupaDodatek: String? = null,
    val drugieDanie: String,
    val dodatki: List<String> = emptyList(),
    val napoj: String,
    val napojTyp: String? = null,
    val cena: Double
)