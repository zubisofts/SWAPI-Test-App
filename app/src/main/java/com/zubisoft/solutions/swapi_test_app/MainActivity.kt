package com.zubisoft.solutions.swapi_test_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.zubisoft.solutions.swapi_test_app.data.PeopleListAdapter
import com.zubisoft.solutions.swapi_test_app.data.Status
import com.zubisoft.solutions.swapi_test_app.databinding.ActivityMainBinding
import com.zubisoft.solutions.swapi_test_app.repository.ApiRepository
import com.zubisoft.solutions.swapi_test_app.viewmodel.AppViewModel
import kotlinx.coroutines.launch
import retrofit2.await

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    
    private lateinit var appViewModel: AppViewModel
    private lateinit var binding:ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        val adapter=PeopleListAdapter()
        binding.peopleList.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        binding.peopleList.adapter=adapter

        appViewModel.getPeople().observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    Log.i(TAG, "onCreate: ${it.status}")
                }
                Status.SUCCESS -> {
                    appViewModel.viewModelScope.launch {
                        val data = it.data!!.await()
                        adapter.setList( data!!.results)
                    }
                }
                else -> {
                    Log.i(TAG, "onCreate: Error=> ${it.message}")
                }
            }
        })

    }

    private fun setupViewModel() {
        appViewModel = ViewModelProvider.NewInstanceFactory().create(AppViewModel::class.java)
    }
}