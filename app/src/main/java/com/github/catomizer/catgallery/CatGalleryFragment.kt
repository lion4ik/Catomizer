package com.github.catomizer.catgallery

import android.os.Bundle
import android.view.View
import com.github.catomizer.R
import com.github.catomizer.di.ComponentManager
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class CatGalleryFragment : MvpAppCompatFragment(R.layout.fragment_cat_gallery), CatGalleryView {

    @InjectPresenter
    lateinit var presenter: CatGalleryPresenter

    @ProvidePresenter
    fun providePresenter(): CatGalleryPresenter = ComponentManager.appComponent.providePresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.toString()
    }
}