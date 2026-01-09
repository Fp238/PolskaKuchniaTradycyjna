package com.example.polskakuchniatradycyjna.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.polskakuchniatradycyjna.model.Order
import com.example.polskakuchniatradycyjna.model.PersonOrder


class OrderViewModel : ViewModel() {

    private val order = Order()

    private val _currentOrder = MutableLiveData<PersonOrder?>()
    val currentOrder: LiveData<PersonOrder?> = _currentOrder

    private val _orders = MutableLiveData<List<PersonOrder>>(emptyList())
    val orders: LiveData<List<PersonOrder>> = _orders

    private val _totalSum = MutableLiveData(0.0)
    val totalSum: LiveData<Double> = _totalSum


    private fun calculatePrice(
        zupa: String,
        drugie: String,
        napoj: String
    ): Double {
        var sum = 0.0
        if (zupa != "Brak") sum += 10.0
        if (drugie != "Brak") sum += 25.0
        if (napoj != "Brak") sum += 5.0
        return sum
    }


    fun setOrder(
        zupa: String,
        zupaDodatek: String?,
        drugie: String,
        dodatki: List<String>,
        napoj: String,
        napojTyp: String?
    ) {
        val price = calculatePrice(zupa, drugie, napoj)

        _currentOrder.value = PersonOrder(
            zupa = zupa,
            zupaDodatek = zupaDodatek,
            drugieDanie = drugie,
            dodatki = dodatki,
            napoj = napoj,
            napojTyp = napojTyp,
            cena = price
        )
    }


    fun confirmCurrentOrder() {
        _currentOrder.value?.let {
            order.addOrder(it)
            _orders.value = order.getOrders()
            _totalSum.value = order.getTotalSum()
            _currentOrder.value = null
        }
    }

    fun clearAllOrders() {
        order.clear()
        _orders.value = emptyList()
        _totalSum.value = 0.0
    }
}