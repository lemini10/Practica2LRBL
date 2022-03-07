package com.example.practica2lrbl

data class Sneaker(var brand: SneakerBrands, var size: Double, var model: String, var id: String)

enum class SneakerBrands {
    Jordan ,Yeezy ,Adidas ,Nike
}