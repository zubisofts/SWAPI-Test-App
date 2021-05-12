package com.zubisoft.solutions.swapi_test_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.zubisoft.solutions.swapi_test_app.data.Resource
import com.zubisoft.solutions.swapi_test_app.model.Character
import com.zubisoft.solutions.swapi_test_app.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppViewModel:ViewModel() {

    private var apiRepository: ApiRepository= ApiRepository()

    fun getPeople() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRepository.fetchPeople()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}