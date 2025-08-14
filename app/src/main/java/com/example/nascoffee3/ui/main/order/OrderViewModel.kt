package com.example.nascoffee3.ui.main.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nascoffee3.data.model.OrderItem

class OrderViewModel : ViewModel() {

    private val _orderItems = MutableLiveData<MutableList<OrderItem>>(mutableListOf())
    val orderItems: LiveData<MutableList<OrderItem>> = _orderItems

    fun addOrderItem(item: OrderItem) {
        val list = _orderItems.value ?: mutableListOf()
        val existingItem = list.find { it.id == item.id && it.details == item.details }

        if (existingItem != null) {
            existingItem.quantity += item.quantity
        } else {
            list.add(item)
        }
        _orderItems.value = list
    }

    fun increaseItemQuantity(item: OrderItem) {
        val list = _orderItems.value ?: return
        val itemInCart = list.find { it.id == item.id }
        itemInCart?.let {
            it.quantity++
            _orderItems.value = list
        }
    }

    fun decreaseItemQuantity(item: OrderItem) {
        val list = _orderItems.value ?: return
        val itemInCart = list.find { it.id == item.id }
        itemInCart?.let {
            if (it.quantity > 1) {
                it.quantity--
            } else {
                list.remove(it)
            }
            _orderItems.value = list
        }
    }

    fun clearCart() {
        _orderItems.value = mutableListOf()
    }
}
