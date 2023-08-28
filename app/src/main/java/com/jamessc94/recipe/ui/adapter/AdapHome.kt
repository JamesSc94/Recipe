package com.jamessc94.recipe.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jamessc94.recipe.databinding.CellHomeBinding
import com.jamessc94.recipe.model.RecipeAdap
import com.jamessc94.recipe.model.RecipeTypeAdap

class AdapHome(val clickListener: AdapHomeClickListener) : ListAdapter<RecipeAdap, RecyclerView.ViewHolder>(diffUtil){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val item = getItem(position)
                holder.bind(clickListener, item)
            }

        }
    }

    fun filterList(fullList: List<RecipeAdap>, filterList: List<String>)
    {
        val temp = arrayListOf<RecipeAdap>()

        fullList.forEach {
            if(filterList.contains(it.category)){
                temp.add(it)
            }
        }

        submitList(temp)
    }

    class ViewHolder private constructor(val binding: CellHomeBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(clickListener: AdapHomeClickListener, item: RecipeAdap){
            binding.data = item
            binding.click = clickListener

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                return ViewHolder(CellHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))

            }

        }

    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<RecipeAdap>() {

            override fun areItemsTheSame(oldItem: RecipeAdap, newItem: RecipeAdap): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: RecipeAdap, newItem: RecipeAdap): Boolean =
                oldItem == newItem
        }
    }

}

class AdapHomeClickListener(val clickListener: (item: RecipeAdap) -> Unit) {
    fun onClick(item: RecipeAdap) = clickListener(item)

}