package com.jamessc94.recipe.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jamessc94.recipe.R
import com.jamessc94.recipe.model.RecipeType
import com.jamessc94.recipe.model.RecipeTypeAdap
import com.jamessc94.recipe.model.asDatabaseModel
import com.jamessc94.recipe.persistence.RecipeDAO
import com.jamessc94.recipe.persistence.RecipeTypeDAO
import com.jamessc94.recipe.repo.HomeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val recipeDao: RecipeDAO,
    private val recipeTypeDao: RecipeTypeDAO,
    private val homeRepo: HomeRepo,
    application: Application
) : AndroidViewModel(application) {

    var isLoading = MutableLiveData<Boolean>(false)
    var toastMessage = MutableLiveData<String>("")

    val recipe = recipeDao.getRecipeAll()
    val recipeType = recipeTypeDao.getRecipeTypeAll()

    init {
        viewModelScope.launch {
            insertCategory()

            try{
                val response = homeRepo.fetchRecipeList(
                    onStart = { isLoading.value = true },
                    onComplete = { isLoading.value = false },
                    onError = { toastMessage.value = it
                        isLoading.value = false},
                )

                if(response.isSuccessful){
                    recipeDao.insertRecipeList(*response.body()!!.asDatabaseModel())

                }

            }catch (e: java.lang.Exception){
                e.printStackTrace()

            }
        }
    }

    fun insertCategory()
    {
        viewModelScope.launch {
            recipeTypeDao.insertRecipeTypeList(*getApplication<Application>().applicationContext.resources.getStringArray(R.array.recipetypes).asDatabaseModel())
        }
    }

    fun updateCategory(model: RecipeType){
        viewModelScope.launch {
            recipeTypeDao.setRecipeType(model.id.toString(), model.isSelected)
        }
    }
}