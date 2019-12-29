package com.github.catomizer.navigation

import androidx.fragment.app.Fragment
import com.github.catomizer.catgallery.CatGalleryFragment
import com.github.catomizer.favorite.FavoriteCatsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class CatGalleryScreen : SupportAppScreen() {

    override fun getFragment(): Fragment = CatGalleryFragment()
}

class FavoriteCatsScreen : SupportAppScreen() {

    override fun getFragment(): Fragment = FavoriteCatsFragment()
}