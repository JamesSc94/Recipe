package com.jamessc94.recipe.ui.details

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.jamessc94.recipe.model.Recipe
import com.jamessc94.recipe.persistence.RecipeDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsVM @Inject constructor(
    private val recipeDao: RecipeDAO,
    savedStateHandle: SavedStateHandle,
    application: Application
) : AndroidViewModel(application) {

    var recipeDetails = recipeDao.getRecipeByType(savedStateHandle.get<String>("id")!!)
    var detailsMode = MutableLiveData<DetailsMode>(savedStateHandle.get<DetailsMode>("mode")!!)
    var imgPathVM = ""

    enum class DetailsMode {
        NonEdit, Edit, Save, Add
    }

    fun addRecipe(recipe: Recipe)
    {
        viewModelScope.launch {
            recipeDao.insertRecipe(recipe)
        }
    }

    fun updateRecipe(recipe: Recipe)
    {
        viewModelScope.launch {
            recipeDao.updateRecipe(recipe)
        }
    }

    fun deleteRecipe(recipe: Recipe)
    {
        viewModelScope.launch {
            recipeDao.deleteRecipe(recipe)
        }
    }
}