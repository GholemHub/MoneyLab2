package com.gholem.moneylab.repository.network.api

import com.gholem.moneylab.repository.network.dto.TemplateRequest
import com.gholem.moneylab.repository.network.dto.TemplateResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TemplateApi {

    @GET("template/api/url")
    suspend fun getTemplateMethod(): TemplateResponse

    @POST("template/api/url")
    suspend fun saveTemplateMethod(@Body templateRequest: TemplateRequest)
}