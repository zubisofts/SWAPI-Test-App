package com.zubisoft.solutions.swapi_test_app.api

import com.zubisoft.solutions.swapi_test_app.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/people")
    fun listCharacters(): Call<ApiResponse?>
}