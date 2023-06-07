package com.example.orhan_ucar_odev9.model

data class Post(
    val id: String = "",
    val email: String,
    val baslik: String,
    val sehir: String,
    val notlar: String,
    val downloadUrl: String
)
