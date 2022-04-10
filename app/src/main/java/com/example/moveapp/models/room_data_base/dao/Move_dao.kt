package com.example.moveapp.models.room_data_base.dao

import androidx.room.*
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

    @Query("select * from movepopularentity where id = :id_move")
    suspend fun getMovePopularId(id_move: Int): MovePopularEntity

    @Update
    fun editMovePopular(movePopularEntity: MovePopularEntity)

    @Query("select * from movenewplayingentity")
    suspend fun getMoveNewPlaying(): List<MoveNewPlayingEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewPlaying(moveNewPlayingEntity: List<MoveNewPlayingEntity>)

    @Update
    fun editNewPlaying(moveNewPlayingEntity: MoveNewPlayingEntity)

    @Query("select * from movenewplayingentity where id = :id_move_new")
    suspend fun getMoveNewPlayingId(id_move_new: Int): MoveNewPlayingEntity
}