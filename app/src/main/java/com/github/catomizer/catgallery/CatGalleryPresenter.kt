package com.github.catomizer.catgallery

import android.util.Log
import com.github.catomizer.base.BasePresenter
import com.github.catomizer.network.CatApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import ru.terrakok.cicerone.Router

@InjectViewState
class CatGalleryPresenter(private val router: Router,
                          private val catApi: CatApi) : BasePresenter<CatGalleryView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        catApi.getCatImages(100, 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d("DEBUG", it.toString())
                },
                {

                }
            )
            .apply { addDisposable(this) }
    }

}