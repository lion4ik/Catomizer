package com.github.catomizer.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.catomizer.data.db.model.CatDbModel

@Database(entities = [CatDbModel::class], version = 1)
abstract class CatsDatabase : RoomDatabase() {

    abstract fun favoriteCatsDao(): FavoriteCatsDao

    companion object {

        fun build(appContext: Context) =
            Room.databaseBuilder(
                appContext,
                CatsDatabase::class.java, "cats.db"
            ).build()
    }
}