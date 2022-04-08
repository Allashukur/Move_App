package com.example.moveapp.models.room_data_base.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moveapp.models.room_data_base.entity.MoveNewPlayingEntity
import com.example.moveapp.models.room_data_base.entity.MovePopularEntity

@Dao
interface Move_dao {

//    @Insert
//    suspend fun addMoveData(movePopular: MovePopularEntity)

    @Query("select * from movepopularentity")
    suspend fun getMoveData(): List<MovePopularEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovePopularList(movePopular: List<MovePopularEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewPlaying(moveNewPlayingEntity: List<MoveNewPlayingEntity>)

}