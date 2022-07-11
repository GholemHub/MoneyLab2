package com.gholem.moneylab.di

import com.gholem.moneylab.repository.network.api.TemplateApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideTemplateApi(retrofit: Retrofit): TemplateApi = retrofit.create(TemplateApi::class.java)
}