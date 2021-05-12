package com.zubisoft.solutions.swapi_test_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.zubisoft.solutions.swapi_test_app.data.CharacterItemClickListener
import com.zubisoft.solutions.swapi_test_app.data.PeopleListAdapter
import com.zubisoft.solutions.swapi_test_app.data.Status
import com.zubisoft.solutions.swapi_test_app.databinding.ActivityMainBinding
import com.zubisoft.solutions.swapi_test_app.databinding.CharacterDetailsLayoutBinding
import com.zubisoft.solutions.swapi_test_app.model.Character
import com.zubisoft.solutions.swapi_test_app.repository.ApiRepository
import com.zubisoft.solutions.swapi_test_app.viewmodel.AppViewModel
import kotlinx.coroutines.launch
import retrofit2.await

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), CharacterItemClickListener {
    
    private lateinit var appViewModel: AppViewModel
    private lateinit var binding:ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        val adapter=PeopleListAdapter(this)
        binding.peopleList.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        binding.peopleList.adapter=adapter

        appViewModel.getPeople().observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    binding.layoutError.visibility= View.GONE
                    binding.progressCircular.visibility=View.VISIBLE
                }
                Status.SUCCESS -> {
                    appViewModel.viewModelScope.launch {
                        val data = it.data!!.await()
                        adapter.setList( data!!.results)

                        binding.layoutError.visibility= View.GONE
                        binding.progressCircular.visibility=View.GONE
                    }
                }
                else -> {
                    binding.layoutError.visibility= View.VISIBLE
                    binding.progressCircular.visibility=View.GONE
                }
            }
        })

    }

    private fun setupViewModel() {
        appViewModel = ViewModelProvider.NewInstanceFactory().create(AppViewModel::class.java)
    }

    override fun onCharacterClicked(character: Character) {
        showDetails(character)
    }

    private fun showDetails(character: Character) {

        val view=LayoutInflater.from(this).inflate(R.layout.character_details_layout, null);
        val bind:CharacterDetailsLayoutBinding= CharacterDetailsLayoutBinding.bind(view)
        val dialog=AlertDialog.Builder(this)
            .setView(view)
            .show()
        bind.txtName.text=character.name
        bind.txtGender.text=character.gender
        bind.btnOk.setOnClickListener {
            dialog.dismiss()
        }
    }
}