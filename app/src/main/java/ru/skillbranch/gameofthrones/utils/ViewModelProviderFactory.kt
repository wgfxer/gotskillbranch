package ru.skillbranch.gameofthrones.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author Valeriy Minnulin
 */
class ViewModelProviderFactory(
    private val creator: ViewModelCreator
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = creator.create()
        return viewModel as T
    }
}

fun interface ViewModelCreator {
    fun create(): ViewModel
}