package com.artragazzi.valorantapiapp.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    companion object{
        val retrofit = Retrofit.Builder().baseUrl("https://valorant-api.com/").addConverterFactory(GsonConverterFactory.create()).build()
    }



}