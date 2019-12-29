package com.github.catomizer

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar


fun Activity.getDisplaySize(): Point {
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size
}

fun Context.dpToPx(dp: Float): Float =
    dp * (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

fun View.showSnack(@StringRes resId: Int, duration: Int = Snackbar.LENGTH_SHORT) = Snackbar.make(this, resId, duration).show()