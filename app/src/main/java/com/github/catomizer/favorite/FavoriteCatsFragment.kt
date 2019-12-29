package com.github.catomizer.favorite

import android.Manifest
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.github.catomizer.R
import com.github.catomizer.base.BaseFragment
import com.github.catomizer.base.OnSelectionItemsListener
import com.github.catomizer.catgallery.CatAdapter
import com.github.catomizer.data.network.model.CatApiModel
import com.github.catomizer.di.ComponentManager
import com.github.catomizer.ui.showSnack
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_favorite_cats.*
import kotlinx.android.synthetic.main.toolbar.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import permissions.dispatcher.*

@RuntimePermissions
class FavoriteCatsFragment : BaseFragment(R.layout.fragment_favorite_cats), FavoriteCatsView,
    OnSelectionItemsListener {

    @InjectPresenter
    lateinit var presenter: FavoriteCatsPresenter

    private val catAdapter: CatAdapter = CatAdapter(this)

    @ProvidePresenter
    fun providePresenter(): FavoriteCatsPresenter =
        ComponentManager.appComponent.provideFavoriteCatsPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        toolbar.setTitle(R.string.cat_favorites_title)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            presenter.onBackPressed()
        }
        val gridLayoutManager = GridLayoutManager(context, 2)
        recycler_cats.layoutManager = gridLayoutManager
        recycler_cats.setHasFixedSize(true)
        recycler_cats.adapter = catAdapter
    }

    override fun showEmptyCatList() {
        text_empty.visibility = View.VISIBLE
    }

    override fun showCatList(catList: List<CatApiModel>) {
        catAdapter.submitList(catList)
        text_empty.visibility = View.GONE
    }

    override fun onStartSelection() {
        val actionModelCallback: ActionMode.Callback = object : ActionMode.Callback {

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                val selectedItems = catAdapter.getSelectedItems().toMutableList()
                when (item.itemId) {
                    R.id.menu_item_download -> {
                        downloadSelectedItemsWithPermissionCheck(selectedItems)
                    }
                }
                mode.finish()
                return true
            }

            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                mode.menuInflater.inflate(R.menu.menu_action_mode_favorite, menu)
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