package com.github.catomizer.catgallery

import com.github.catomizer.base.ShowErrorView
import com.github.catomizer.network.model.CatApiModel
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface CatGalleryView : ShowErrorView {

    fun setLoadingVisibility(isVisible: Boolean)

    fun showCatList(catList: List<CatApiModel>)
}