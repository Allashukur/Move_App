package com.example.moveapp.models.room_data_base.dao

import androidx.room.*
import com.example.moveapp.models.room_data_base.entity.MoveNewPlayingEntity
import com.example.moveapp.models.room_data_base.entity.MovePopularEntity

@Dao
interface Move_dao {

    @Insert
    suspend fun addMoveDataPopular(movePopular: MovePopularEntity)

    @Query("select * from movepopularentity")
    suspend fun getMoveData(): List<MovePopularEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovePopularList(movePopular: List<MovePopularEntity>)

    @Query("select * from movepopularentity where id = :id_move")
    fun getMovePopularId(id_move: Int): MovePopularEntity

    @Update
    fun editMovePopular(movePopularEntity: MovePopularEntity)

    @Query("select * from movenewplayingentity")
    suspend fun getMoveNewPlaying(): List<MoveNewPlayingEntity>

    @Query("select * from movepopularentity where favrorite = :fav")
    suspend fun getMovePopularFav(fav: Boolean): List<MovePopularEntity>

    @Insert
    suspend fun addNewMove(moveNewPlayingEntity: MoveNewPlayingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewPlaying(moveNewPlayingEntity: List<MoveNewPlayingEntity>)

    @Update
    fun editNewPlaying(moveNewPlayingEntity: MoveNewPlayingEntity)

    @Query("select * from movenewplayingentity where id = :id_move_new")
    fun getMoveNewPlayingId(id_move_new: Int): MoveNewPlayingEntity


    @Query("select * from movenewplayingentity where favrorite = :fav")
    suspend fun getNewMovePlayingFav(fav: Boolean): List<MoveNewPlayingEntity>
}