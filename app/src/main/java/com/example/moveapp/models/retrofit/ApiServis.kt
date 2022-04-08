package com.example.myweather.retrofit

import com.example.moveapp.models.retrofit.model.MoveModel
import com.example.moveapp.models.retrofit.model_now_playing.ModelNowPlayingMove
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServis {

    @GET("/3/movie/popular")
    suspend fun getData(@Query("api_key") apiKey: String): Response<MoveModel>

    @GET("/3/movie/top_rated")
    suspend fun getMoveNowPlaying(@Query("api_key") apiKey: String): Response<ModelNowPlayingMove>


}