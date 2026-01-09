package com.example.polskakuchniatradycyjna.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.polskakuchniatradycyjna.model.Order
import com.example.polskakuchniatradycyjna.model.PersonOrder

class OrderViewModel : ViewModel() {

    private val orderModel = Order()

    // Lista zatwierdzonych zamówień
    private val _currentOrders = MutableLiveData<List<PersonOrder>>(orderModel.getOrders())
    val currentOrders: LiveData<List<PersonOrder>> get() = _currentOrders

    // Łączna suma wszystkich zatwierdzonych zamówień
    private val _totalSum = MutableLiveData<Double>(orderModel.getTotalSum())
    val totalSum: LiveData<Double> get() = _totalSum

    // Bieżące zamówienie w trakcie tworzenia (do TotalBar "na żywo")
    private val _currentOrder = MutableLiveData<PersonOrder>()
    val currentOrder: LiveData<PersonOrder> get() = _currentOrder

    // Dodanie zatwierdzonego zamówienia
    fun addOrder(order: PersonOrder) {
        orderModel.addOrder(order)
        _currentOrders.value = orderModel.getOrders()
        _totalSum.value = orderModel.getTotalSum()
        _currentOrder.value = null // po zatwierdzeniu bieżące zamówienie jest czyszczone
    }

    // Aktualizacja bieżącego zamówienia (np. gdy użytkownik zmienia zupę, dodatki lub napój)
    fun updateCurrentOrder(order: PersonOrder) {
        _currentOrder.value = order
    }

    // Czyszczenie wszystkich zamówień
    fun clearOrders() {
        orderModel.clear()
        _currentOrders.value = orderModel.getOrders()
        _totalSum.value = orderModel.getTotalSum()
        _currentOrder.value = null
    }

    // Opcjonalnie: szybka funkcja zwracająca sumę bieżącego zamówienia
    fun getCurrentOrderSum(): Double {
        return _currentOrder.value?.getTotalPrice() ?: 0.0
    }
}