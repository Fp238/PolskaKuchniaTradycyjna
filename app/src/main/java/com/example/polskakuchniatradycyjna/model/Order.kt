package com.example.polskakuchniatradycyjna.model

class Order {

    private val orders = mutableListOf<PersonOrder>()

    fun addOrder(order: PersonOrder) {
        orders.add(order)
    }

    fun getOrders(): List<PersonOrder> = orders

    fun getTotalSum(): Double {
        return orders.sumOf { it.cena }
    }

    fun clear() {
        orders.clear()
    }
}