package com.github.catomizer.data.network.model

import com.github.catomizer.data.db.model.CatDbModel

data class CatApiModel(val id: String, val url: String){

    fun toDbModel(): CatDbModel = CatDbModel(id, url)
}