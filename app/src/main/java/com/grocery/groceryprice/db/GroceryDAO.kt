package com.grocery.groceryprice.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grocery.groceryprice.model.Grocery
import com.grocery.groceryprice.utils.Constants

@Dao
interface GroceryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGroceries(prices: List<Grocery>)

    @Query("SELECT * FROM Grocery")
    suspend fun getAllGroceries(): List<Grocery>

    @Query("SELECT * FROM Grocery WHERE CASE " +
            "WHEN :district != '' AND :market != '' THEN district = :district AND market = :market " +
            "WHEN :district != '' AND :market = '' THEN district = :district END")
    suspend fun getFilteredGroceries(district: String, market: String): List<Grocery>

    @Query("SELECT * FROM Grocery WHERE CASE " +
            "WHEN :district != '' AND :market != '' THEN district = :district AND market = :market " +
            "WHEN :district != '' AND :market = '' THEN district = :district "+
            "ELSE id != 0 END ORDER BY " +
            "CASE WHEN :orderBy = '${Constants.TAG_PRICE}' AND :sortBy = 1 THEN modal_price END ASC," +
            "CASE WHEN :orderBy = '${Constants.TAG_PRICE}' AND :sortBy = 0 THEN modal_price END DESC," +
            "CASE WHEN :orderBy = '${Constants.TAG_DATE}' AND :sortBy = 1 THEN arrival_date END ASC," +
            "CASE WHEN :orderBy = '${Constants.TAG_DATE}' AND :sortBy = 0 THEN arrival_date END DESC")
    suspend fun getFilteredSortedGroceries(district: String, market: String, orderBy: String, sortBy: Int): List<Grocery>

    @Query("SELECT DISTINCT district FROM Grocery")
    suspend fun getDistricts(): List<String>

    @Query("SELECT DISTINCT market FROM Grocery WHERE district = :district")
    suspend fun getMarkets(district: String): List<String>

    @Query("DELETE FROM Grocery")
    suspend fun deleteAll()

}