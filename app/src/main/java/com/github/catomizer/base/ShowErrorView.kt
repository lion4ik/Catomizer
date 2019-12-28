package com.github.catomizer.base

import androidx.annotation.StringRes
import moxy.MvpView

interface ShowErrorView: MvpView {

    fun showError(@StringRes strResId: Int)
}