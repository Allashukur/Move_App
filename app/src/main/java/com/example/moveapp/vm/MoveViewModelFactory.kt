package com.example.moveapp.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moveapp.models.room_data_base.app_data_base.AppDatabase
import com.example.myweather.retrofit.ApiServis
import uz.mobiler.mvvmg23.utils.NetworkHelper
import java.lang.RuntimeException

class MoveViewModelFactory(
    private val networkHelper: NetworkHelper,
    private val apiServis: ApiServis,
    private val appDatabase: AppDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoveViewModel::class.java)) {
            return MoveViewModel(apiServis, networkHelper, appDatabase) as T
        }
        throw  RuntimeException("Error")
    }


}