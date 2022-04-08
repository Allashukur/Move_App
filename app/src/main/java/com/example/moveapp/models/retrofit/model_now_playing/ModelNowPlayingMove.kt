package com.example.moveapp.models.retrofit.model_now_playing

data class ModelNowPlayingMove(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)