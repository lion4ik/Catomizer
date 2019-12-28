package com.github.catomizer.catgallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.github.catomizer.R
import com.github.catomizer.base.OnSelectedItemsCallback
import com.github.catomizer.network.model.CatApiModel
import kotlinx.android.synthetic.main.item_cat.view.*

class CatAdapter(
    private val onSelectedItemsCallback: OnSelectedItemsCallback<CatApiModel>,
    private val displayWidth: Int
) : ListAdapter<CatApiModel, CatViewHolder>(diffUtilCallback) {

    private val selectedItems: MutableSet<Int> = mutableSetOf()
    private var isSelectionMode = false

    companion object {
        private val diffUtilCallback = object : ItemCallback<CatApiModel>() {

            override fun areItemsTheSame(oldItem: CatApiModel, newItem: CatApiModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CatApiModel, newItem: CatApiModel): Boolean =
                oldItem == newItem
        }
    }

    fun clearSelection() {
        isSelectionMode = false
        selectedItems.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val holder = CatViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_cat,
                parent,
                false
            )
        )
        holder.itemView.setOnLongClickListener {
            onSelectedItemsCallback.onStartSelection()
            selectedItems.add(holder.adapterPosition)
            notifyItemChanged(holder.adapterPosition)
            isSelectionMode = true
            true
        }
        holder.itemView.setOnClickListener {
            if (isSelectionMode) {
                if (selectedItems.contains(holder.adapterPosition)) {
                    selectedItems.remove(holder.adapterPosition)
                } else {
                    selectedItems.add(holder.adapterPosition)
                }
                notifyItemChanged(holder.adapterPosition)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        if (selectedItems.contains(position)) {
            holder.itemView.image_selected.visibility = View.VISIBLE
            holder.itemView.image_cat.alpha = 0.5F
        } else {
            holder.itemView.image_selected.visibility = View.GONE
            holder.itemView.image_cat.alpha = 1F
        }
        val height =
            holder.itemView.context.resources.getDimensionPixelSize(R.dimen.cat_image_height)
        Glide.with(holder.itemView)
            .load(getItem(position).url)
            .placeholder(R.drawable.ic_placeholder)
            .override(displayWidth / 2, height)
            .centerCrop()
            .into(holder.itemView.image_cat)
    }
}