package com.github.catomizer.catgallery

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.catomizer.R
import com.github.catomizer.base.OnSelectedItemsCallback
import com.github.catomizer.di.ComponentManager
import com.github.catomizer.getDisplaySize
import com.github.catomizer.network.model.CatApiModel
import com.github.catomizer.showSnack
import kotlinx.android.synthetic.main.fragment_cat_gallery.*
import kotlinx.android.synthetic.main.toolbar.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class CatGalleryFragment : MvpAppCompatFragment(R.layout.fragment_cat_gallery), CatGalleryView,
    OnSelectedItemsCallback<CatApiModel> {

    @InjectPresenter
    lateinit var presenter: CatGalleryPresenter

    private var isLoading = false

    @ProvidePresenter
    fun providePresenter(): CatGalleryPresenter = ComponentManager.appComponent.providePresenter()

    private val actionModelCallback: ActionMode.Callback = object : ActionMode.Callback {

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return true
        }

        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            val menuInflater = MenuInflater(context)
            menuInflater.inflate(R.menu.menu_download, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            val adapter = recycler_cats.adapter
            if (adapter is CatAdapter) {
                adapter.clearSelection()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        val gridLayoutManager = GridLayoutManager(context, 2)
        recycler_cats.layoutManager = gridLayoutManager
        recycler_cats.setHasFixedSize(true)
        recycler_cats.adapter = CatAdapter(this, requireActivity().getDisplaySize().x)
        recycler_cats.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) //check for scroll down
                {
                    val visibleItemCount = gridLayoutManager.childCount
                    val totalItemCount = gridLayoutManager.itemCount
                    val pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition()

                    if (!isLoading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            presenter.requestCatImages()
                        }
                    }
                }
            }
        })
    }

    override fun setLoadingVisibility(isVisible: Boolean) {
        progress_bar.visibility = if (isVisible) View.VISIBLE else View.GONE
        isLoading = isVisible
    }

    override fun showCatList(catList: List<CatApiModel>) {
        val adapter = recycler_cats.adapter
        if (adapter is CatAdapter) {
            adapter.submitList(catList)
        }
    }

    override fun showError(strResId: Int) {
        view?.showSnack(strResId)
    }

    override fun onStartSelection() {
        toolbar.startActionMode(actionModelCallback)
    }

    override fun onItemsSelected(items: List<CatApiModel>) {
    }
}