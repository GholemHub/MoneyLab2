package com.gholem.moneylab.di

import com.gholem.moneylab.repository.storage.TemplateStorageRepository
import com.gholem.moneylab.repository.storage.TemplateStorageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTemplateStorageRepository(templateStorageRepositoryImpl: TemplateStorageRepositoryImpl): TemplateStorageRepository
}