package com.example.nascoffee3.data.repository

import com.example.nascoffee3.data.local.dao.CoffeeDao
import com.example.nascoffee3.data.local.entity.Coffee
import kotlinx.coroutines.flow.Flow

class CoffeeRepository(private val coffeeDao: CoffeeDao) {

    val allCoffee: Flow<List<Coffee>> = coffeeDao.getAllCoffee()

    /**
     * FUNGSI BARU: Meneruskan permintaan untuk mengambil satu kopi
     * dari DAO.
     */
    fun getCoffee(id: Int): Flow<Coffee> {
        return coffeeDao.getCoffeeById(id)
    }

    suspend fun insert(coffee: Coffee) {
        coffeeDao.insert(coffee)
    }
}
