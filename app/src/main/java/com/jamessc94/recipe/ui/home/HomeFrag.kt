package com.jamessc94.recipe.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jamessc94.recipe.R
import com.jamessc94.recipe.databinding.FragHomeBinding
import com.jamessc94.recipe.model.asDisplayList
import com.jamessc94.recipe.ui.adapter.AdapHome
import com.jamessc94.recipe.ui.adapter.AdapHomeClickListener
import com.jamessc94.recipe.ui.details.DetailsVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFrag : Fragment() {

    private val vm : HomeVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.vm = vm
        binding.adapter = AdapHome(AdapHomeClickListener{
            Bundle().apply {
                this.putString("id", it.id)
                this.putSerializable("mode", DetailsVM.DetailsMode.NonEdit)
                findNavController().navigate(R.id.action_homeFrag_to_detailsFrag, this)
            }
        })

        vm.recipe.observe(viewLifecycleOwner, Observer {
            (binding.adapter as AdapHome).apply {
               this.submitList(it.asDisplayList())

                vm.recipe.value?.let { recipe ->
                    vm.recipeType.value?.let { type ->
                        this.filterList(recipe.asDisplayList(),type.filter { it.isSelected }.map { it.strCategory })
                    }
                }
            }
        })

        vm.recipeType.observe(viewLifecycleOwner, Observer {
            (binding.adapter as AdapHome).apply {
                vm.recipe.value?.let { recipe ->
                    vm.recipeType.value?.let { type ->
                        this.filterList(recipe.asDisplayList(),type.filter { it.isSelected }.map { it.strCategory })
                    }
                }
            }
        })

        binding.fragHomeFilter.setOnClickListener {
            HomeDialogFrag.newInstance().show(childFragmentManager, "category")
        }

        binding.fragHomeAdd.setOnClickListener {
            Bundle().apply {
                this.putString("id", "0")
                this.putSerializable("mode", DetailsVM.DetailsMode.Add)
                findNavController().navigate(R.id.action_homeFrag_to_editFrag, this)
            }
        }

        return binding.root

    }
}