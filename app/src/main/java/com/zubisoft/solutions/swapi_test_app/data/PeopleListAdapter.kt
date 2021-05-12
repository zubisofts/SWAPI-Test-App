package com.zubisoft.solutions.swapi_test_app.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.zubisoft.solutions.swapi_test_app.R
import com.zubisoft.solutions.swapi_test_app.databinding.PeopleListItemBinding
import com.zubisoft.solutions.swapi_test_app.model.Character

private var characters:List<Character> = listOf()

class PeopleListAdapter(val characterItemClickListener: CharacterItemClickListener): RecyclerView.Adapter<PeopleListAdapter.PeopleItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleItemViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.people_list_item, parent,false)
        return PeopleItemViewHolder(PeopleListItemBinding.bind(view))
    }

    override fun getItemCount(): Int {
       return characters.size
    }

    override fun onBindViewHolder(holder: PeopleItemViewHolder, position: Int) {
        holder.bind.txtName.text= characters[position].name
        holder.itemView.setOnClickListener {
            characterItemClickListener.onCharacterClicked(character = characters[position])
        }
    }

    fun setList(list:List<Character>){
        characters=list
        notifyDataSetChanged()
    }
    inner class PeopleItemViewHolder(val bind: PeopleListItemBinding) :RecyclerView.ViewHolder(bind.root){}
}

interface CharacterItemClickListener{
    fun onCharacterClicked(character: Character)
}

