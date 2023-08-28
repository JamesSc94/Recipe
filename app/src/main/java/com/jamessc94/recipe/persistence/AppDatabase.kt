package com.jamessc94.recipe.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jamessc94.recipe.model.Recipe
import com.jamessc94.recipe.model.RecipeType

@Database(entities = [Recipe::class, RecipeType::class], version = 9, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDAO(): RecipeDAO
    abstract fun recipeTypeDAO(): RecipeTypeDAO

    companion object {
        private var INSTANCE : AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase {
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "recipe_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance

                }
                return instance
            }
        }
    }
}