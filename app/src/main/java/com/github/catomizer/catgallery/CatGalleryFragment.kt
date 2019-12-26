package com.github.catomizer.catgallery

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.github.catomizer.R
import com.github.catomizer.di.ComponentManager
import com.github.catomizer.network.model.CatApiModel
import kotlinx.android.synthetic.main.fragment_cat_gallery.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class CatGalleryFragment : MvpAppCompatFragment(R.layout.fragment_cat_gallery), CatGalleryView {

    @InjectPresenter
    lateinit var presenter: CatGalleryPresenter

    @ProvidePresenter
    fun providePresenter(): CatGalleryPresenter = ComponentManager.appComponent.providePresenter()

    private val catAdapter = CatAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        recycler_cats.layoutManager = GridLayoutManager(context, 2)
        recycler_cats.setHasFixedSize(true)
        recycler_cats.adapter = catAdapter
    }

    override fun showCatList(catList: List<CatApiModel>) {
        catAdapter.submitList(catList)
    }
}