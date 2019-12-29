package com.github.catomizer.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.catomizer.data.db.model.CatDbModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FavoriteCatsDao {

    @Query("SELECT * FROM favorite_cats")
    fun getAll(): Single<List<CatDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCats(cat: List<CatDbModel>): Completable
}