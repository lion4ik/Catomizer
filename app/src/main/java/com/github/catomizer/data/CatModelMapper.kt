package com.github.catomizer.data

import com.github.catomizer.data.db.model.CatDbModel
import com.github.catomizer.data.network.model.CatApiModel

class CatModelMapper: Mapper<CatApiModel, CatDbModel> {

    override fun mapTo(obj: CatApiModel): CatDbModel =
        CatDbModel(obj.id, obj.url)

    override fun mapFrom(obj: CatDbModel): CatApiModel =
        CatApiModel(obj.id, obj.imageUrl)
}