package com.example.moveapp.models.retrofit.model

data class MoveModel(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)