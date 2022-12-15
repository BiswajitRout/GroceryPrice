package com.grocery.groceryprice.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.grocery.groceryprice.db.GroceryDB
import com.grocery.groceryprice.model.Grocery
import com.grocery.groceryprice.retrofit.ApiUrls
import javax.inject.Inject

class GroceryRepository @Inject constructor(
    private val apiUrls: ApiUrls,
    private val groceryDB: GroceryDB
) {
    private val _groceries = MutableLiveData<List<Grocery>>()
    val groceries: LiveData<List<Grocery>>
        get() = _groceries

    private val _districts = MutableLiveData<List<String>>()
    val districts: LiveData<List<String>>
        get() = _districts

    private val _markets = MutableLiveData<List<String>>()
    val markets: LiveData<List<String>>
        get() = _markets

    suspend fun getGroceries() {
        val records = groceryDB.getGroceryDao().getAllGroceries()
        if (records.isNotEmpty()) {
            _groceries.postValue(records)
        } else {
            val result = apiUrls.getGroceries()
            if (result.isSuccessful && result.body()?.records != null) {
                groceryDB.getGroceryDao().addGroceries(result.body()?.records!!)
                _groceries.postValue(result.body()?.records)
            }
        }
    }

    suspend fun getFilteredSortedGroceries(
        district: String,
        market: String,
        orderBy: String,
        sortBy: Int
    ) {
        val records =
            groceryDB.getGroceryDao().getFilteredSortedGroceries(district, market, orderBy, sortBy)
        _groceries.postValue(records)
    }

    suspend fun getFilteredGroceries(
        district: String,
        market: String
    ) {
        val records =
            groceryDB.getGroceryDao().getFilteredGroceries(district, market)
        _groceries.postValue(records)
    }

    suspend fun getDistricts() {
        val records =
            groceryDB.getGroceryDao().getDistricts()
        _districts.postValue(records)
    }

    suspend fun getMarkets(district: String) {
        val records =
            groceryDB.getGroceryDao().getMarkets(district)
        _markets.postValue(records)
    }
}