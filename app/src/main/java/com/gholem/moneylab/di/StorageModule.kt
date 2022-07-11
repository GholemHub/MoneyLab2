package com.gholem.moneylab.di

import android.content.Context
import androidx.room.Room
import com.gholem.moneylab.repository.storage.MoneyLabDatabase
import com.gholem.moneylab.repository.storage.dao.TemplateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    private const val DATABASE_NAME = "moneylab-db"

    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): MoneyLabDatabase =
        Room.databaseBuilder(
            context,
            MoneyLabDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Provides
    fun provideTemplateDao(database: MoneyLabDatabase): TemplateDao =
        database.templateDao()
}