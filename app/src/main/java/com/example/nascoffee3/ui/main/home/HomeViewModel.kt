package com.example.nascoffee3.ui.main.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.nascoffee3.data.local.database.NasCoffeeDatabase
import com.example.nascoffee3.data.local.entity.Coffee
import com.example.nascoffee3.data.repository.CoffeeRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CoffeeRepository
    val allCoffee: LiveData<List<Coffee>>

    init {
        val coffeeDao = NasCoffeeDatabase.getDatabase(application).coffeeDao()
        repository = CoffeeRepository(coffeeDao)
        allCoffee = repository.allCoffee.asLiveData()
        insertSampleData()
    }

    private fun insertSampleData() = viewModelScope.launch {

        if (repository.allCoffee.asLiveData().value.isNullOrEmpty()) {
            val sampleCoffees = listOf(
                Coffee(name = "Americano", description = "Strong and bold", price = 2.50, imageUrl = "americano"),
                Coffee(name = "Cappuccino", description = "Rich and foamy", price = 3.00, imageUrl = "cappuccino"),
                Coffee(name = "Flat White", description = "Smooth and creamy", price = 3.00, imageUrl = "flat_white"),
                Coffee(name = "Raf", description = "A quick shot", price = 2.00, imageUrl = "raf"),
                Coffee(name = "Espresso", description = "A quick shot", price = 2.00, imageUrl = "espresso"),
                Coffee(name = "Latte", description = "Mild and milky", price = 3.25, imageUrl = "latte"),
            )

            sampleCoffees.forEach { coffee ->
                repository.insert(coffee)
            }
        }
    }
}
