package com.gholem.moneylab.di

import com.gholem.moneylab.repository.network.TemplateApiRepository
import com.gholem.moneylab.repository.network.TransactionApiRepository
import com.gholem.moneylab.repository.network.api.TemplateApi
import com.gholem.moneylab.repository.network.api.TransactionApi
import com.gholem.moneylab.repository.network.real.TemplateApiNetworkRepository
import com.gholem.moneylab.repository.network.real.TransactionApiNetworkRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideTemplateApi(retrofit: Retrofit): TemplateApi =
        retrofit.create(TemplateApi::class.java)

    @Singleton
    @Provides
    fun provideTemplateApiRepository(
        templateApi: TemplateApi
    ): TemplateApiRepository =
//        if(isFake){
//            TemplateApiFakeRepository()
//        }else{
        TemplateApiNetworkRepository(templateApi)
    // }

    @Singleton
    @Provides
    fun provideTransactionApiRepository(
        transactionApi: TransactionApi
    ): TransactionApiRepository =
        TransactionApiNetworkRepositoryImpl(transactionApi)
}