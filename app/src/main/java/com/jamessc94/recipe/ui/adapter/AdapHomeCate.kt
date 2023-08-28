package com.jamessc94.recipe.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jamessc94.recipe.databinding.CellHomeCateBinding
import com.jamessc94.recipe.model.RecipeTypeAdap

class AdapHomeCate(val clickListener: AdapHomeCateClickListener) : ListAdapter<RecipeTypeAdap, RecyclerView.ViewHolder>(diffUtil) {

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

    class ViewHolder private constructor(val binding: CellHomeCateBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(clickListener: AdapHomeCateClickListener, item: RecipeTypeAdap){
            binding.data = item
            binding.click = clickListener

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                return ViewHolder(CellHomeCateBinding.inflate(LayoutInflater.from(parent.context), parent, false))

            }

        }

    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<RecipeTypeAdap>() {

            override fun areItemsTheSame(oldItem: RecipeTypeAdap, newItem: RecipeTypeAdap): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: RecipeTypeAdap, newItem: RecipeTypeAdap): Boolean =
                oldItem == newItem
        }
    }
}

class AdapHomeCateClickListener(val clickListener: (item: RecipeTypeAdap) -> Unit) {
    fun onClick(item: RecipeTypeAdap) = clickListener(item)

}