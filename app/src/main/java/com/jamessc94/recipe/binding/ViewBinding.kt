package com.jamessc94.recipe.binding

import android.graphics.drawable.TransitionDrawable
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

object ViewBinding {

    @JvmStatic
    @BindingAdapter("toast")
    fun bindToast(view: View, text: String?){
        if(!text.isNullOrEmpty()){
            Toast.makeText(view.context, text, Toast.LENGTH_LONG).show()

        }

    }

    @JvmStatic
    @BindingAdapter("visibilityPb")
    fun bindVisibilityPb(view: ProgressBar, b: Boolean){
        view.visibility = if(b) View.VISIBLE else View.GONE

    }
}