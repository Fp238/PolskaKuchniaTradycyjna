package com.example.polskakuchniatradycyjna.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.polskakuchniatradycyjna.model.Order
import com.example.polskakuchniatradycyjna.model.PersonOrder

class OrderViewModel : ViewModel() {

    private val orderModel = Order()

    private val _currentOrders = MutableLiveData<List<PersonOrder>>(orderModel.getOrders())
    val currentOrders: LiveData<List<PersonOrder>> get() = _currentOrders

    private val _totalSum = MutableLiveData<Double>(orderModel.getTotalSum())
    val totalSum: LiveData<Double> get() = _totalSum

    fun addOrder(order: PersonOrder) {
        orderModel.addOrder(order)
        _currentOrders.value = orderModel.getOrders()
        _totalSum.value = orderModel.getTotalSum()
    }

    fun clearOrders() {
        orderModel.clear()
        _currentOrders.value = orderModel.getOrders()
        _totalSum.value = orderModel.getTotalSum()
    }
}
