package com.grocery.groceryprice.model

data class GroceryResponse(
    val created: Int,
    val message: String,
    val records: List<Grocery>,
    val status: String,
    val updated: Int
)