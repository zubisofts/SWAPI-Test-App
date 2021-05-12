package com.zubisoft.solutions.swapi_test_app.api

import com.zubisoft.solutions.swapi_test_app.data.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SWAPIClient {
    private var INSTATNCE: Retrofit? = null
    fun getInstance(): Retrofit? {
        if (INSTATNCE == null) {
            INSTATNCE = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return INSTATNCE
    }
}