package com.gholem.moneylab.di

import com.gholem.moneylab.repository.storage.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTemplateStorageRepository(templateStorageRepositoryImpl: TemplateStorageRepositoryImpl): TemplateStorageRepository

    @Binds
    abstract fun bindTransactionStorageRepository(transactionStorageRepositoryImpl: TransactionStorageRepositoryImpl): TransactionStorageRepository

    @Binds
    abstract fun bindCategoryStorageRepository(categoryStorageRepositoryImpl: CategoryStorageRepositoryImpl): CategoryStorageRepository
}