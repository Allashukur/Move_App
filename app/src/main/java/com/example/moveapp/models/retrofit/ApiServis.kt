package com.example.myweather.retrofit

import com.example.moveapp.models.retrofit.model.MoveModel
import com.example.moveapp.models.retrofit.model_now_playing.ModelNowPlayingMove
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiServis {

    @GET("/3/movie/popular")
    suspend fun getData(@Query("api_key") apiKey: String): Response<MoveModel>

    @GET("/3/movie/now_playing")
    suspend fun getMoveNowPlaying(@Query("api_key") apiKey: String): Response<ModelNowPlayingMove>


}