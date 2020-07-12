package com.androiddevs.mvvmnewsapp.ui.framgnets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androiddevs.mvvmnewsapp.ui.framgnets.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.ui.framgnets.viewmodels.NewsViewModel

class NewsViewModelProviderFactory (
    val newsRepository: NewsRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}