package com.gholem.moneylab.features.planning.adapter.item

data class Address(
    val buildingNumber: String,
    val city: String,
    val country: String,
    val county_code: String,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val street: String,
    val streetName: String,
    val zipcode: String
)