package com.jamessc94.recipe

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RecipeApp : Application() {

    companion object {
        var imgPath = ""
    }

}