package com.grocery.groceryprice.retrofit

import com.grocery.groceryprice.model.GroceryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiUrls {

    @GET("resource/9ef84268-d588-465a-a308-a864a43d0070")
    suspend fun getGroceries(
        @Query("api-key") key: String = "579b464db66ec23bdd00000112c5767d0e754d98655001e29e0e09f8",
        @Query("format") format: String = "json",
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 1000
    ): Response<GroceryResponse>
}