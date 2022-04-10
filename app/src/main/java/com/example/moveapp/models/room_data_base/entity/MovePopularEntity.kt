package com.example.moveapp.models.room_data_base.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovePopularEntity(
    @PrimaryKey()
    val id: Int,
    val title: String,
    val image_url: String,
    val description: String,
    val release_date: String,
    val rank: String,
    var favrorite: Boolean
) {

}