package com.example.nascoffee3.ui.main.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel ini akan dibagikan untuk menyimpan data pengguna yang sedang login.
 */
class UserViewModel : ViewModel() {

    // Menyimpan nama pengguna.
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    init {
        // Untuk saat ini, kita atur nama default di sini.
        // Nanti, data ini akan diambil dari database setelah login berhasil.
        _userName.value = "Adrian"
    }

    // Fungsi untuk memperbarui nama pengguna (akan kita gunakan nanti)
    fun setUserName(name: String) {
        _userName.value = name
    }
}
