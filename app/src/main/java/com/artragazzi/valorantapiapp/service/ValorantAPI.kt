package com.artragazzi.valorantapiapp.service

import com.artragazzi.valorantapiapp.model.Agente
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ValorantAPI {

    @Headers(
        "content-type: application/json",
        "Accept-Encoding: application/gzip"
    )
    @GET("v1/agents")
    suspend fun getAllAgents(@Query("language") language: String = "pt-BR"): Response<Any>

}