package com.artragazzi.valorantapiapp.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

        companion object{
            val retrofit = Retrofit.Builder()
                .baseUrl("https://valorant-agents-maps-arsenal.p.rapidapi.com/").addConverterFactory(
                    GsonConverterFactory.create())
                .build()
        }

}