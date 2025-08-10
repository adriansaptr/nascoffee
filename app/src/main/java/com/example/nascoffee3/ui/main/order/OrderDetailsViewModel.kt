package com.example.nascoffee3.ui.main.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.example.nascoffee3.data.local.database.NasCoffeeDatabase
import com.example.nascoffee3.data.local.entity.Coffee
import com.example.nascoffee3.data.repository.CoffeeRepository

class OrderDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CoffeeRepository

    private val _coffeeId = MutableLiveData<Int>()
    val coffee: LiveData<Coffee>

    // LiveData untuk menyimpan kuantitas saat ini
    private val _quantity = MutableLiveData<Int>(1)
    val quantity: LiveData<Int> = _quantity

    // LiveData untuk menyimpan harga total
    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> = _totalPrice

    init {
        val coffeeDao = NasCoffeeDatabase.getDatabase(application).coffeeDao()
        repository = CoffeeRepository(coffeeDao)
        coffee = _coffeeId.switchMap { id ->
            repository.getCoffee(id).asLiveData()
        }
    }

    fun start(id: Int) {
        _coffeeId.value = id
    }

    // Fungsi untuk menambah kuantitas
    fun increaseQuantity() {
        val currentQuantity = _quantity.value ?: 1
        _quantity.value = currentQuantity + 1
        calculateTotalPrice()
    }

    // Fungsi untuk mengurangi kuantitas
    fun decreaseQuantity() {
        val currentQuantity = _quantity.value ?: 1
        if (currentQuantity > 1) {
            _quantity.value = currentQuantity - 1
            calculateTotalPrice()
        }
    }

    // Fungsi untuk menghitung harga total
    fun calculateTotalPrice() {
        val currentCoffee = coffee.value
        val currentQuantity = _quantity.value ?: 1
        currentCoffee?.let {
            _totalPrice.value = it.price * currentQuantity
        }
    }
}
