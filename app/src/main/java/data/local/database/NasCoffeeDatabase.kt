package com.example.nascoffee3.data.local.database // DIPERBAIKI: Menggunakan nama paket Anda

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nascoffee3.data.local.dao.CoffeeDao
import com.example.nascoffee3.data.local.entity.Coffee

/**
 * Anotasi @Database adalah untuk memberitahu Room bahwa ini adalah kelas database utama.
 * - entities = [...] -> Mendaftarkan semua 'blueprint' (Entity) yang akan ada di database ini.
 * Untuk saat ini kita baru punya Coffee. Nanti kita bisa tambahkan User, Order, dll.
 * - version = 1 -> Versi database. Jika Anda mengubah struktur tabel (misal menambah kolom),
 * Anda harus menaikkan nomor versi ini.
 * - exportSchema = false -> Menonaktifkan ekspor skema database, tidak apa-apa untuk proyek sederhana.
 */
@Database(entities = [Coffee::class], version = 1, exportSchema = false)
abstract class NasCoffeeDatabase : RoomDatabase() {

    /**
     * Database harus tahu tentang semua 'resepsionis' (DAO) yang dimilikinya.
     * Fungsi abstract ini akan diimplementasikan secara otomatis oleh Room.
     */
    abstract fun coffeeDao(): CoffeeDao
    // Nanti kita bisa tambahkan: abstract fun orderDao(): OrderDao

    /**
     * Companion object ini seperti static di Java. Tujuannya adalah agar kita hanya membuat
     * SATU instance/objek dari database ini untuk seluruh aplikasi (pola Singleton).
     * Ini penting untuk mencegah kebocoran memori dan error.
     */
    companion object {
        @Volatile
        private var INSTANCE: NasCoffeeDatabase? = null

        fun getDatabase(context: Context): NasCoffeeDatabase {
            // Jika INSTANCE sudah ada, langsung kembalikan.
            // Jika belum, buat database baru di dalam blok synchronized.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NasCoffeeDatabase::class.java,
                    "nas_coffee_database" // Nama file database yang akan disimpan di perangkat
                ).build()
                INSTANCE = instance
                // kembalikan instance
                instance
            }
        }
    }
}
