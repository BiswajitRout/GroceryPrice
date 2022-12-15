package com.grocery.groceryprice.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Grocery(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val arrival_date: String,
    val commodity: String,
    val state: String,
    val district: String,
    val market: String,
    val max_price: String,
    val min_price: String,
    val modal_price: String,
    val variety: String
)