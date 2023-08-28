package com.jamessc94.recipe.ui.edit

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.jamessc94.recipe.R
import com.jamessc94.recipe.RecipeApp.Companion.imgPath
import com.jamessc94.recipe.databinding.FragEditBinding
import com.jamessc94.recipe.model.Recipe
import com.jamessc94.recipe.permission.Permission
import com.jamessc94.recipe.permission.PermissionManager
import com.jamessc94.recipe.ui.details.DetailsVM
import com.jamessc94.recipe.ui.dialog.DialogCameraStorage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditFrag : Fragment() {

    private val vm : DetailsVM by viewModels()
    private lateinit var binding : FragEditBinding
    val permissionManager = PermissionManager.from(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragEditBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.vm = vm

        val aa = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, requireActivity().resources.getStringArray(R.array.recipetypes))
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.fragEditSpinner.adapter = aa

        binding.fragEditBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fragEditSave.setOnClickListener {
            vm.detailsMode.value.let {
                it.let {
                    when(it) {
                        DetailsVM.DetailsMode.Save -> {
                            vm.recipeDetails.value?.let { rec ->
                                rec.strMeal = binding.fragEditTitleEt.text.toString().trim()
                                rec.strCategory = binding.fragEditSpinner.selectedItem.toString()
                                rec.strIngredient = binding.fragEditIng.text.toString().trim()
                                rec.strInstructions = binding.fragEditIns.text.toString().trim()
                                if(vm.imgPathVM.isNotEmpty()){
                                    rec.strMealThumb = vm.imgPathVM
                                }
                                vm.updateRecipe(rec)

                                findNavController().popBackStack()
                            }
                        }
                        DetailsVM.DetailsMode.Add -> {
                            if(binding.fragEditTitleEt.text.toString().trim().isNotEmpty() &&
                                binding.fragEditIng.text.toString().trim().isNotEmpty() &&
                                binding.fragEditIns.text.toString().trim().isNotEmpty() &&
                                vm.imgPathVM.isNotEmpty()){

                                val rec = Recipe()
                                rec.idMeal = (0..1000).random().toString()
                                rec.strMeal = binding.fragEditTitleEt.text.toString().trim()
                                rec.strCategory = binding.fragEditSpinner.selectedItem.toString()
                                rec.strIngredient = binding.fragEditIng.text.toString().trim()
                                rec.strInstructions = binding.fragEditIns.text.toString().trim()
                                rec.strMealThumb = vm.imgPathVM

                                vm.addRecipe(rec)

                                findNavController().popBackStack()
                            }
                        }
                        else -> {}
                    }
                }
            }
        }

        binding.fragEditImgAdd.setOnClickListener {
            permissionManager.request(Permission.Camera, Permission.Storage).checkPermission {
                DialogCameraStorage.newInstance().show(childFragmentManager, "camera_storage")
            }
        }

        vm.recipeDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.data = it

                if(it.strIngredient.isNotEmpty()) {
                    binding.fragEditIng.setText(it.strIngredient)
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

                    binding.fragEditIng.setText(ing)
                }
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        if(imgPath.isNotEmpty()){
            vm.imgPathVM = imgPath
            Glide.with(requireContext()).load(vm.imgPathVM).into(binding.fragEditImg)

            imgPath = ""
        }
    }
}