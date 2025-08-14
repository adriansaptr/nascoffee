package com.example.nascoffee3.data.model

// DIPERBARUI: Menambahkan properti untuk kustomisasi
data class OrderItem(
    val id: Int,
    val name: String,
    var details: String, // Akan kita isi dengan detail kustomisasi
    val price: Double,
    var quantity: Int,
    val imageUrl: String,
    // Properti baru untuk menyimpan kustomisasi
    val ristrettoType: String? = null,
    val locationType: String? = null,
    val volume: String? = null,
    val roastingLevel: Int? = null,
    val grindingSize: String? = null,
    val iceLevel: Int? = null,
    val coffeeSort: String? = null,
    val milkType: String? = null,
    val syrupType: String? = null,
    val additives: List<String>? = null
)
