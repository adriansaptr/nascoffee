package com.example.nascoffee3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.nascoffee3.R // DIPERBAIKI: Menambahkan import untuk R class

/**
 * MainActivity sekarang mewarisi dari AppCompatActivity, yang merupakan
 * kelas dasar untuk Activity yang menggunakan XML Views dan fitur kompatibilitas.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Panggil installSplashScreen() SEBELUM super.onCreate()
        // Ini akan menjaga splash screen tetap tampil sampai aplikasi siap.
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // Baris ini sangat penting.
        // Ia memberitahu Activity untuk memuat dan menampilkan layout dari
        // file res/layout/activity_main.xml yang telah kita buat.
        setContentView(R.layout.activity_main)
    }
}
