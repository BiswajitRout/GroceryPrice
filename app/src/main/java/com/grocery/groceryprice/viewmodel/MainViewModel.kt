package com.grocery.groceryprice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grocery.groceryprice.R
import com.grocery.groceryprice.model.Grocery
import com.grocery.groceryprice.repository.GroceryRepository
import com.grocery.groceryprice.utils.Constants
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val groceryRepository: GroceryRepository
) : ViewModel() {

    var selectedDistrict: String = ""
    var selectedMarket: String = ""
    var selectedOrderBy: String = ""
    var selectedSortBy: Int = 0

    val groceriesLiveData: LiveData<List<Grocery>>
    get() = groceryRepository.groceries

    val districtsLiveData: LiveData<List<String>>
    get() = groceryRepository.districts

    val marketsLiveData: LiveData<List<String>>
    get() = groceryRepository.markets

    init {
        viewModelScope.launch {
            groceryRepository.getGroceries()
            groceryRepository.getDistricts()
        }
    }

    /**Set data according to selected radio button*/
    fun updateOrderBy(checkedId: Int) {
        when (checkedId) {
            R.id.rbNone -> {
                selectedOrderBy = ""
            }
            R.id.rbPriceAsc -> {
                selectedOrderBy = Constants.TAG_PRICE
                selectedSortBy = 1
            }
            R.id.rbPriceDesc -> {
                selectedOrderBy = Constants.TAG_PRICE
                selectedSortBy = 0
            }
            R.id.rbDateAsc -> {
                selectedOrderBy = Constants.TAG_DATE
                selectedSortBy = 1
            }
            R.id.rbDateDesc -> {
                selectedOrderBy = Constants.TAG_DATE
                selectedSortBy = 0
            }
        }
    }

    /**
     * If district and order by is selected then we will filter and order the records
     * If district is selected but order by is not selected then we will filter the data without sorting
     * If order by is selected without any district and market we will sort all the records in DAO without any filter
     * */
    fun filterRecords() {
        viewModelScope.launch(Dispatchers.IO) {
            if(selectedDistrict.isNotEmpty() && selectedOrderBy.isEmpty()) {
                groceryRepository.getFilteredGroceries(selectedDistrict, selectedMarket)
            } else if(selectedOrderBy.isNotEmpty()) {
                groceryRepository.getFilteredSortedGroceries(selectedDistrict, selectedMarket, selectedOrderBy, selectedSortBy)
            }
        }
    }

    fun getMarketsOfDistrict() {
        viewModelScope.launch(Dispatchers.IO) {
            groceryRepository.getMarkets(selectedDistrict)
        }
    }

    fun clearFilter() {
        selectedDistrict = ""
        selectedMarket = ""
        selectedOrderBy = ""
        selectedSortBy = 0
        viewModelScope.launch(Dispatchers.IO) {
            groceryRepository.getGroceries()
        }
    }
}