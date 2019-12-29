package com.github.catomizer.ui

import android.app.Activity
import android.graphics.Point
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar


fun Activity.getDisplaySize(): Point {
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size
}

fun View.showSnack(@StringRes resId: Int, duration: Int = Snackbar.LENGTH_SHORT) =
    Snackbar.make(this, resId, duration).show()