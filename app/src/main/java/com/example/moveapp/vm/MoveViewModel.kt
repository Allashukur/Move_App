package com.example.moveapp.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moveapp.models.room_data_base.app_data_base.AppDatabase
import com.example.moveapp.models.room_data_base.entity.MoveNewPlayingEntity
import com.example.moveapp.models.room_data_base.entity.MovePopularEntity
import com.example.moveapp.resource.MoveResource
import com.example.moveapp.respository.MoveRepository
import com.example.myweather.retrofit.ApiServis
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.mobiler.mvvmg23.utils.NetworkHelper
import java.lang.Exception

class MoveViewModel(
    apiServis: ApiServis,
    private val networkHelper: NetworkHelper,
    appDatabase: AppDatabase
) : ViewModel() {

    private val moveRepository = MoveRepository(apiServis, appDatabase)

    fun getMove(): StateFlow<MoveResource> {
        val flow = MutableStateFlow<MoveResource>(MoveResource.Loading)

        viewModelScope.launch {
            try {
                if (networkHelper.isNetworkConnected()) {
                    val response = async { moveRepository.getAllMoveDataFromRemote() }
                    val response2 = async { moveRepository.getNewMovePlaying() }

                    if (response.await().isSuccessful && response2.await().isSuccessful) {
                        val list = ArrayList<MovePopularEntity>()
                        val list2 = ArrayList<MoveNewPlayingEntity>()

                        response.await().body()?.results?.forEach {
                            list.add(
                                MovePopularEntity(
                                    it.id,
                                    it.title,
                                    "https://image.tmdb.org/t/p/w500/" + it.poster_path,
                                    it.overview,
                                    it.release_date,
                                    it.vote_average.toString()
                                )
                            )
                        }
                        response2.await().body()?.results?.forEach {
                            list2.add(
                                MoveNewPlayingEntity(
                                    it.id,
                                    it.original_title,
                                    it.overview,
                                    "https://image.tmdb.org/t/p/w500/" + it.poster_path,
                                    it.release_date,
                                    it.vote_average.toString(),
                                    it.original_language
                                )
                            )
                        }

                        moveRepository.addMoveDataBase(list)
                        moveRepository.addMoveNewPlaying(list2)
                        flow.emit(MoveResource.Succes(list, list2))
                    } else {
                        flow.emit(MoveResource.Error(response.await().errorBody().toString()))
                    }
                }

            } catch (e: Exception) {
                flow.emit(MoveResource.Error(e.message.toString()))
            }
        }
        return flow
    }

}