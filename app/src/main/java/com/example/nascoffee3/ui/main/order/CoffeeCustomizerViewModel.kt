package com.example.nascoffee3.ui.main.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CoffeeCustomizerViewModel : ViewModel() {

    val roastingLevel = MutableLiveData(3)
    val grindingSize = MutableLiveData("Large")
    val iceLevel = MutableLiveData(0)
    val coffeeSort = MutableLiveData("Arabica")
    val milkType = MutableLiveData("Whole Milk")
    val syrupType = MutableLiveData("Vanilla")
    val additives = MutableLiveData<List<String>>(emptyList())

    fun setRoastingLevel(level: Int) {
        roastingLevel.value = level
    }

    fun setGrindingSize(size: String) {
        grindingSize.value = size
    }

    fun setIceLevel(level: Int) {
        if (iceLevel.value == level) {
            iceLevel.value = 0
        } else {
            iceLevel.value = level
        }
    }
}
