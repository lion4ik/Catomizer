package com.github.catomizer.catgallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.github.catomizer.R
import com.github.catomizer.network.model.CatApiModel
import kotlinx.android.synthetic.main.item_cat.view.*

class CatAdapter : ListAdapter<CatApiModel, CatViewHolder>(diffUtilCallback) {

    companion object {
        private val diffUtilCallback = object : ItemCallback<CatApiModel>() {

            override fun areItemsTheSame(oldItem: CatApiModel, newItem: CatApiModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CatApiModel, newItem: CatApiModel): Boolean =
                oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cat, parent, false))
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(getItem(position).url)
            .override(600, 500)
            .centerCrop()
            .into(holder.itemView.image_cat)
    }
}