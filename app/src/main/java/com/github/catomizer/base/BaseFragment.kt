package com.github.catomizer.base

import androidx.annotation.LayoutRes
import com.github.catomizer.ui.showSnack
import moxy.MvpAppCompatFragment

open class BaseFragment(@LayoutRes contentLayoutId: Int) : MvpAppCompatFragment(contentLayoutId),
    ShowMessageView {

    override fun showMessage(strResId: Int) {
        view?.showSnack(strResId)
    }

}