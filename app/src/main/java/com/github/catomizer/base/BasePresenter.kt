package com.github.catomizer.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import moxy.MvpView

abstract class BasePresenter<V : MvpView>: MvpPresenter<V>() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) = disposables.add(disposable)

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}