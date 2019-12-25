package com.github.catomizer.navigation

import androidx.fragment.app.Fragment
import com.github.catomizer.catgallery.CatGalleryFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class Screen : SupportAppScreen() {

    class CatGalleryScreen : Screen() {

        override fun getFragment(): Fragment = CatGalleryFragment()
    }
}