package com.github.catomizer.catgallery

import com.github.catomizer.network.model.CatApiModel
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface CatGalleryView: MvpView {

    fun showCatList(catList: List<CatApiModel>)
}