package com.grocery.groceryprice.di

import android.content.Context
import androidx.room.Room
import com.grocery.groceryprice.db.GroceryDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun getDb(@ApplicationContext context: Context): GroceryDB {
        return Room.databaseBuilder(context, GroceryDB::class.java, "GroceryDB").build()
    }
}