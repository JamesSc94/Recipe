package com.jamessc94.recipe.di

import android.content.Context
import com.jamessc94.recipe.persistence.AppDatabase
import com.jamessc94.recipe.persistence.RecipeDAO
import com.jamessc94.recipe.persistence.RecipeTypeDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Singleton
    @Provides
    fun provideWeatherDatabase(@ApplicationContext context : Context) = AppDatabase.getInstance(context)

    @Provides
    fun provideRecipeDAO(appDatabase: AppDatabase): RecipeDAO {
        return appDatabase.recipeDAO()
    }

    @Provides
    fun provideRecipeTypeDAO(appDatabase: AppDatabase): RecipeTypeDAO {
        return appDatabase.recipeTypeDAO()
    }
}