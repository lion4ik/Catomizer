package com.github.catomizer.catgallery

import android.Manifest
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.catomizer.R
import com.github.catomizer.base.OnSelectionItemsListener
import com.github.catomizer.di.ComponentManager
import com.github.catomizer.getDisplaySize
import com.github.catomizer.network.model.CatApiModel
import com.github.catomizer.showSnack
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_cat_gallery.*
import kotlinx.android.synthetic.main.toolbar.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import permissions.dispatcher.*

@RuntimePermissions
class CatGalleryFragment : MvpAppCompatFragment(R.layout.fragment_cat_gallery), CatGalleryView,
    OnSelectionItemsListener {

    @InjectPresenter
    lateinit var presenter: CatGalleryPresenter

    private var isLoading = false

    @ProvidePresenter
    fun providePresenter(): CatGalleryPresenter = ComponentManager.appComponent.providePresenter()

    private val actionModelCallback: ActionMode.Callback = object : ActionMode.Callback {

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            val adapter = recycler_cats.adapter
            if (adapter is CatAdapter) {
                downloadSelectedItemsWithPermissionCheck(adapter.getSelectedItems().toMutableList())
            }
            mode.finish()
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
        toolbar.setTitle(R.string.cat_gallery_title)
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
        button_try_again.setOnClickListener {
            presenter.requestCatImages()
        }
    }

    override fun setLoadingVisibility(isVisible: Boolean) {
        progress_bar.visibility = if (isVisible) View.VISIBLE else View.GONE
        isLoading = isVisible
    }

    override fun showEmptyCatList() {
        group_empty_list.visibility = View.VISIBLE
    }

    override fun showCatList(catList: List<CatApiModel>) {
        val adapter = recycler_cats.adapter
        if (adapter is CatAdapter) {
            adapter.submitList(catList)
            group_empty_list.visibility = View.GONE
        }
    }

    override fun showError(strResId: Int) {
        view?.showSnack(strResId)
    }

    override fun onStartSelection() {
        toolbar.startActionMode(actionModelCallback)
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onWriteExternalStorageDenied() {
        view?.showSnack(R.string.cat_gallery_permission_denied, Snackbar.LENGTH_LONG)
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onWriteExternalStorageNeverAskAgain() {
        view?.showSnack(R.string.cat_gallery_permission_never_ask, Snackbar.LENGTH_LONG)
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun showRationalWriteExternalStorage(permissionRequest: PermissionRequest) {
        view?.showSnack(R.string.cat_gallery_permission_rationale, Snackbar.LENGTH_LONG)
        permissionRequest.proceed()
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun downloadSelectedItems(items: List<CatApiModel>) {
        presenter.downloadCatImages(items)
    }

    override fun shouldShowRequestPermissionRationale(permission: String): Boolean =
        Manifest.permission.WRITE_EXTERNAL_STORAGE == permission

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode, grantResults)
    }
}