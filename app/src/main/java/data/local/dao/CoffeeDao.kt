package com.example.nascoffee3.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nascoffee3.data.local.entity.Coffee
import kotlinx.coroutines.flow.Flow

@Dao
interface CoffeeDao {

    @Query("SELECT * FROM coffee_table ORDER BY name ASC")
    fun getAllCoffee(): Flow<List<Coffee>>

    /**
     * FUNGSI BARU: Mengambil satu item kopi dari tabel
     * berdasarkan ID yang diberikan.
     */
    @Query("SELECT * FROM coffee_table WHERE id = :coffeeId")
    fun getCoffeeById(coffeeId: Int): Flow<Coffee>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coffee: Coffee)

}
