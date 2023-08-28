package com.jamessc94.recipe.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImageViewBinding {

    @JvmStatic
    @BindingAdapter("urlValue")
    fun bindUrlImg(view: ImageView, urlValue: String?){
        urlValue.let {
            Glide.with(view.context).load(urlValue).into(view)
        }
    }
}