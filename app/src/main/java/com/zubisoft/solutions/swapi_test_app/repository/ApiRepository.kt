package com.zubisoft.solutions.swapi_test_app.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.zubisoft.solutions.swapi_test_app.api.ApiService
import com.zubisoft.solutions.swapi_test_app.api.SWAPIClient
import com.zubisoft.solutions.swapi_test_app.model.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "ApiRepository"

class ApiRepository {
    private val service: ApiService =
        SWAPIClient.getInstance()!!.create(ApiService::class.java)

    suspend fun fetchPeople()=service.listCharacters()

}