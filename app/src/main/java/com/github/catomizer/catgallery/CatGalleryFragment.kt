package com.github.catomizer.catgallery

import android.Manifest
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.catomizer.R
import com.github.catomizer.base.BaseFragment
import com.github.catomizer.base.OnSelectionItemsListener
import com.github.catomizer.data.network.model.CatApiModel
import com.github.catomizer.di.ComponentManager
import com.github.catomizer.ui.showSnack
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_cat_gallery.*
import kotlinx.android.synthetic.main.toolbar.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import permissions.dispatcher.*

@RuntimePermissions
class CatGalleryFragment : BaseFragment(R.layout.fragment_cat_gallery), CatGalleryView,
    OnSelectionItemsListener {

    @InjectPresenter
    lateinit var presenter: CatGalleryPresenter

    private var isLoading = false
    private val catAdapter: CatAdapter = CatAdapter(this)

    @ProvidePresenter
    fun providePresenter(): CatGalleryPresenter =
        ComponentManager.appComponent.provideCatGalleryPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initToolbar()
        val gridLayoutManager = GridLayoutManager(context, 2)
        recycler_cats.layoutManager = gridLayoutManager
        recycler_cats.setHasFixedSize(true)
        recycler_cats.adapter = catAdapter
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

    private fun initToolbar() {
        toolbar.inflateMenu(R.menu.menu_favorite)
        toolbar.setTitle(R.string.cat_gallery_title)
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_item_favorite) {
                presenter.onFavoritesClicked()
            }
            true
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
        catAdapter.submitList(catList)
        group_empty_list.visibility = View.GONE
    }

    override fun onStartSelection() {
        val actionModelCallback: ActionMode.Callback = object : ActionMode.Callback {

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                val selectedItems = catAdapter.getSelectedItems().toMutableList()
                when (item.itemId) {
                    R.id.menu_item_download -> {
                        downloadSelectedItemsWithPermissionCheck(selectedItems)
                    }
                    R.id.menu_item_favorite -> presenter.onAddToFavoriteClicked(selectedItems)
                }
                mode.finish()
                return true
            }

            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                mode.menuInflater.inflate(R.menu.menu_action_mode_cat_gallery, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode) {
                catAdapter.clearSelection()
            }
        }
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