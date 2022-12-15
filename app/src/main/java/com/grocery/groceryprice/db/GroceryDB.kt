package com.grocery.groceryprice.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.grocery.groceryprice.db.converter.DateConverter
import com.grocery.groceryprice.model.Grocery

@Database(entities = [Grocery::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class GroceryDB: RoomDatabase() {

    abstract fun getGroceryDao(): GroceryDAO
}