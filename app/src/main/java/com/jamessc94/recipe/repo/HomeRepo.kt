package com.jamessc94.recipe.repo

import com.jamessc94.recipe.model.RecipeListContainer
import com.jamessc94.recipe.network.Network
import retrofit2.Response
import javax.inject.Inject

class HomeRepo @Inject constructor() {

    suspend fun fetchRecipeList(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
        ) : Response<RecipeListContainer> {

        onStart()

        Network.retroRecipe.fetchRecipeList("").apply {
            if(this.isSuccessful){
                onComplete()

            }else{
                if(this.errorBody()!!.string().contains("invalid arguments", ignoreCase = true)){
                    onError("No Data Available")

                }else{
                    onError(this.errorBody()!!.string())

                }
            }
            return this
        }

    }

}