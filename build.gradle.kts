plugins {
    // Plugin untuk aplikasi Android, jangan dihapus
    id("com.android.application") version "8.9.2" apply false
    // Plugin untuk Kotlin Android, jangan dihapus
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
    // INI YANG PALING PENTING: Tambahkan plugin KSP di sini
    id("com.google.devtools.ksp") version "1.9.23-1.0.20" apply false
}