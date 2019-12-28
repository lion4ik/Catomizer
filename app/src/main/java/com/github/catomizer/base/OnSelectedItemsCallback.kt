package com.github.catomizer.base

interface OnSelectedItemsCallback<T> {

    fun onStartSelection()

    fun onItemsSelected(items: List<T>)
}