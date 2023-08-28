package com.jamessc94.recipe.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jamessc94.recipe.model.Recipe
import com.jamessc94.recipe.model.RecipeType

@Dao
interface RecipeDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertRecipeList(vararg updates : Recipe)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertRecipe(vararg updates : Recipe)

    @Query("SELECT * FROM Recipe WHERE idMeal = :recipetypeid")
    fun getRecipeByType(recipetypeid: String?): LiveData<Recipe?>

    @Query("SELECT * FROM Recipe")
    fun getRecipeAll() : LiveData<List<Recipe>>

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)
}

@Dao
interface RecipeTypeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeTypeList(vararg updates : RecipeType)

    @Query("SELECT * FROM RecipeType")
    fun getRecipeTypeAll() : LiveData<List<RecipeType>>

    @Query("UPDATE RecipeType SET isSelected = :isSelected WHERE id = :id")
    suspend fun setRecipeType(id: String, isSelected: Boolean)

}