package com.github.catomizer.favorite

import com.github.catomizer.base.ShowMessageView
import com.github.catomizer.data.network.model.CatApiModel
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface FavoriteCatsView : ShowMessageView {

    fun showEmptyCatList()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showCatList(catList: List<CatApiModel>)
}