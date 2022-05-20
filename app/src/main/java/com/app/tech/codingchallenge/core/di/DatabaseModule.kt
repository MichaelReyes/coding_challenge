package com.app.tech.codingchallenge.core.di

import android.content.Context
import androidx.room.Room
import com.app.tech.codingchallenge.core.data.db.AppDatabase
import com.app.tech.codingchallenge.core.data.db.dao.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePostDao(appDataBase: AppDatabase): PostDao {
        return appDataBase.postDao()
    }

}