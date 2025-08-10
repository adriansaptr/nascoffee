package com.example.nascoffee3.ui.main.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nascoffee3.data.model.OrderItem

/**
 * ViewModel ini akan dibagikan di antara beberapa Fragment.
 * Fungsinya adalah sebagai keranjang belanja virtual.
 */
class OrderViewModel : ViewModel() {

    // Menyimpan daftar item yang ada di keranjang.
    // _orderItems bersifat private agar hanya bisa diubah dari dalam ViewModel ini.
    private val _orderItems = MutableLiveData<MutableList<OrderItem>>(mutableListOf())

    // orderItems bersifat public dan hanya bisa dibaca (read-only) oleh UI.
    val orderItems: LiveData<MutableList<OrderItem>> = _orderItems

    /**
     * Fungsi untuk menambahkan item baru ke keranjang.
     * Jika item dengan ID yang sama sudah ada, ia akan menambah jumlahnya.
     */
    fun addOrderItem(item: OrderItem) {
        val list = _orderItems.value ?: mutableListOf()
        val existingItem = list.find { it.id == item.id }

        if (existingItem != null) {
            // Jika sudah ada, tambah quantity-nya
            existingItem.quantity += item.quantity
        } else {
            // Jika belum ada, tambahkan sebagai item baru
            list.add(item)
        }
        // Memicu pembaruan pada LiveData
        _orderItems.value = list
    }

    /**
     * Fungsi untuk menambah kuantitas item di keranjang.
     */
    fun increaseItemQuantity(item: OrderItem) {
        val list = _orderItems.value ?: return
        val itemInCart = list.find { it.id == item.id }
        itemInCart?.let {
            it.quantity++
            _orderItems.value = list // Memicu pembaruan
        }
    }

    /**
     * Fungsi untuk mengurangi kuantitas item di keranjang.
     * Jika kuantitas menjadi 0, item akan dihapus.
     */
    fun decreaseItemQuantity(item: OrderItem) {
        val list = _orderItems.value ?: return
        val itemInCart = list.find { it.id == item.id }
        itemInCart?.let {
            if (it.quantity > 1) {
                it.quantity--
            } else {
                // Jika kuantitas 1, hapus item dari keranjang
                list.remove(it)
            }
            _orderItems.value = list // Memicu pembaruan
        }
    }
}
