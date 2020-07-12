package com.androiddevs.mvvmnewsapp.ui.framgnets.db

import android.content.Context
import androidx.room.*
import com.androiddevs.mvvmnewsapp.ui.framgnets.models.Article


@Database(
    entities = [Article::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class ArticleDB : RoomDatabase() {

    abstract fun getArticleDAO(): ArticleDAO

    companion object {
        @Volatile
        private var instance: ArticleDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDB::class.java,
                "article_db.db"
            ).build()


    }

}