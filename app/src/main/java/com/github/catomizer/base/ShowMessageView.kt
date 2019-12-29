package com.github.catomizer.base

import androidx.annotation.StringRes
import moxy.MvpView

interface ShowMessageView: MvpView {

    fun showMessage(@StringRes strResId: Int)
}