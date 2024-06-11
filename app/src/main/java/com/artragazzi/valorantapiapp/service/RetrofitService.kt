package com.artragazzi.valorantapiapp.service

import com.artragazzi.valorantapiapp.model.Agente
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("agents/en-us")
    fun getAgent(@Query("name") name: String): Response<Agente>
}