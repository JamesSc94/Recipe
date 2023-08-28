package com.jamessc94.recipe.network

import com.jamessc94.recipe.model.RecipeListContainer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeService {

    @GET("search.php")
    suspend fun fetchRecipeList(@Query("s") search: String): Response<RecipeListContainer>

}