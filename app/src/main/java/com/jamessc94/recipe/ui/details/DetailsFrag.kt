package com.jamessc94.recipe.ui.details

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jamessc94.recipe.R
import com.jamessc94.recipe.RecipeApp
import com.jamessc94.recipe.databinding.FragDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFrag : Fragment() {

    private val vm : DetailsVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.vm = vm

        binding.fragDetailsBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fragDetailsDelete.setOnClickListener {
            vm.recipeDetails.value?.let {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle(getString(R.string.confirm_to_delete))

                builder.setPositiveButton(android.R.string.ok) { _, _ ->
                    vm.deleteRecipe(it)

                    findNavController().popBackStack()
                }

                builder.setNegativeButton(android.R.string.cancel) { _, _ ->

                }

                builder.show()
            }

        }

        binding.fragDetailsEdit.setOnClickListener {
            Bundle().apply {
                this.putString("id", vm.recipeDetails.value!!.idMeal)
                this.putSerializable("mode", DetailsVM.DetailsMode.Save)
                findNavController().navigate(R.id.action_detailsFrag_to_editFrag, this)
            }
        }

        vm.recipeDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.data = it

                if(it.strIngredient.isNotEmpty()) {
                    binding.fragDetailsIngEt.text = it.strIngredient
                }else{
                    var ing = it.strIngredient1

                    if(it.strIngredient2.isNotEmpty()) {
                        ing += ", " + it.strIngredient2
                    }

                    if(it.strIngredient3.isNotEmpty()) {
                        ing += ", " + it.strIngredient3
                    }

                    if(it.strIngredient4.isNotEmpty()) {
                        ing += ", " + it.strIngredient4
                    }

                    if(it.strIngredient5.isNotEmpty()) {
                        ing += ", " + it.strIngredient5
                    }

                    if(it.strIngredient6.isNotEmpty()) {
                        ing += ", " + it.strIngredient6
                    }

                    if(it.strIngredient7.isNotEmpty()) {
                        ing += ", " + it.strIngredient7
                    }

                    if(it.strIngredient8.isNotEmpty()) {
                        ing += ", " + it.strIngredient8
                    }

                    if(it.strIngredient9.isNotEmpty()) {
                        ing += ", " + it.strIngredient9
                    }

                    if(it.strIngredient10.isNotEmpty()) {
                        ing += ", " + it.strIngredient10
                    }

                    binding.fragDetailsIngEt.text = ing
                }
            }
        })

        return binding.root
    }
}