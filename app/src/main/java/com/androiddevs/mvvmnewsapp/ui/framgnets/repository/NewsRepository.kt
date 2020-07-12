package com.androiddevs.mvvmnewsapp.ui.framgnets.repository

import com.androiddevs.mvvmnewsapp.ui.framgnets.api.RetrofitInstance
import com.androiddevs.mvvmnewsapp.ui.framgnets.db.ArticleDB
import com.androiddevs.mvvmnewsapp.ui.framgnets.models.Article

class NewsRepository(
    val db: ArticleDB
) {

    suspend fun getBreakingNews(countryCode:String,pageNumber:Int)=
        RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)


    suspend fun searchNews(searchQuery:String, pageNumber: Int)=
        RetrofitInstance.api.searchForNews(searchQuery,pageNumber)


    suspend fun upsert(article:Article)=db.getArticleDAO().upsert(article)

    fun getSavedNews()=db.getArticleDAO().getAllArticles()

    suspend fun deleteArticel(article: Article)=db.getArticleDAO().deleteArticle(article)
}