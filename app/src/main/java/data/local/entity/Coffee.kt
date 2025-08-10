package com.example.nascoffee3.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Anotasi @Entity memberitahu Room (library database kita)
 * bahwa kelas ini adalah sebuah cetakan untuk tabel di dalam database.
 * Kita beri nama tabelnya "coffee_table".
 */
@Entity(tableName = "coffee_table")
data class Coffee(

    /**
     * @PrimaryKey menandakan bahwa 'id' adalah kunci unik untuk setiap baris di tabel.
     * autoGenerate = true berarti Room akan secara otomatis memberikan nilai id
     * yang unik untuk setiap kopi baru yang kita tambahkan (misalnya 1, 2, 3, ...).
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Kolom untuk menyimpan nama kopi, contoh: "Americano"
    val name: String,

    // Kolom untuk menyimpan deskripsi singkat kopi
    val description: String,

    // Kolom untuk menyimpan harga kopi
    val price: Double,

    /**
     * Kolom untuk menyimpan path ke gambar kopi.
     * Ini bisa berupa URL dari internet atau nama file drawable dari aplikasi kita.
     * Contoh: "americano_image" (jika disimpan di drawable)
     * atau "https://example.com/americano.png" (jika dari internet).
     */
    val imageUrl: String
)