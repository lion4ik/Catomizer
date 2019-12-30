package com.github.catomizer.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_cats")
data class CatDbModel(
    @PrimaryKey
    @ColumnInfo(name = "cat_id")
    val id: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String
)