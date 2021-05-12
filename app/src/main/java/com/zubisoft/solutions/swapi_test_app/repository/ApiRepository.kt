package com.zubisoft.solutions.swapi_test_app.repository

import com.zubisoft.solutions.swapi_test_app.api.ApiService
import com.zubisoft.solutions.swapi_test_app.api.SWAPIClient

private const val TAG = "ApiRepository"

class ApiRepository {
    private val service: ApiService =
        SWAPIClient.getInstance()!!.create(ApiService::class.java)

    fun fetchPeople()=service.listCharacters()

}