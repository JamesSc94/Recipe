package com.jamessc94.recipe.utils

import android.content.res.Resources
import android.graphics.Rect
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

fun DialogFragment.setWidthPercent(w: Int, h: Int = 0) {
    val dm = Resources.getSystem().displayMetrics
    val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
    val percentWidth = rect.width() * (w.toFloat() / 100)

    if(h > 0){
        val percentHeight = rect.height() * (h.toFloat() / 100)
        dialog?.window?.setLayout(percentWidth.toInt(), if(h > 0){ percentHeight.toInt() } else {percentHeight.toInt()})

    }else{
        dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)

    }
}