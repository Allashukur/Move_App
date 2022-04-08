package com.example.moveapp.models.retrofit

import com.example.myweather.retrofit.ApiServis
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiClient {

    const val BASE_URL = "https://api.themoviedb.org/"

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiServis = getRetrofit().create(ApiServis::class.java)

}