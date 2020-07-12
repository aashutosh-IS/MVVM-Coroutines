package com.androiddevs.mvvmnewsapp.ui.framgnets.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.ui.framgnets.models.Article
import com.androiddevs.mvvmnewsapp.ui.framgnets.models.NewsResponse
import com.androiddevs.mvvmnewsapp.ui.framgnets.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.ui.framgnets.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response


class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    var breakingNewsPage = 1

    var breakingNewsRes: NewsResponse? = null

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    var searchNewsPage = 1
    var searchNewsRes: NewsResponse? = null


    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNews(response))

    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleBreakingNews(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                breakingNewsPage++
                if (breakingNewsRes == null) {
                    breakingNewsRes = it
                } else {
                    val oldArticles = breakingNewsRes?.articles
                    val newArticle = it.articles
                    oldArticles?.addAll(newArticle)
                }
                return Resource.Success(breakingNewsRes ?: it)
            }
        }
        return Resource.Error(response.message())
    }


    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                searchNewsPage++
                if (searchNewsRes == null) {
                    searchNewsRes = it
                } else {
                    val oldArticles = searchNewsRes?.articles
                    val newArticle = it.articles
                    oldArticles?.addAll(newArticle)
                }
                return Resource.Success(searchNewsRes ?: it)
            }
        }
        return Resource.Error(response.message())
    }


    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticel(article)
    }

}