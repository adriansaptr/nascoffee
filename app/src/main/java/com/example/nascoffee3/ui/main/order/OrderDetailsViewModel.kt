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

    // Data Kopi Asli dari Database
    private val _coffeeId = MutableLiveData<Int>()
    val coffee: LiveData<Coffee>

    // --- State Kustomisasi Pesanan (Otak yang Mengingat Pilihan) ---
    val quantity = MutableLiveData(1)
    val ristrettoType = MutableLiveData("One")
    val locationType = MutableLiveData("Onsite")
    val volume = MutableLiveData("350 ml")
    val coffeeSort = MutableLiveData("Arabica")
    val milkType = MutableLiveData("Whole Milk")
    val syrupType = MutableLiveData("Vanilla")
    val additives = MutableLiveData<List<String>>(emptyList())
    val roastingLevel = MutableLiveData(3)
    val grindingSize = MutableLiveData("Large")
    val iceLevel = MutableLiveData(0)

    private val _totalPrice = MutableLiveData(0.0)
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

    fun calculateTotalPrice() {
        val coffeePrice = coffee.value?.price ?: 0.0
        val currentQuantity = quantity.value ?: 1
        _totalPrice.value = coffeePrice * currentQuantity
    }
}
