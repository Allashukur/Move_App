package com.example.moveapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.moveapp.MainActivity
import com.example.moveapp.R
import com.example.moveapp.adapter.AdapterFavRv
import com.example.moveapp.databinding.FragmentFavouriteBinding
import com.example.moveapp.databinding.FragmentTopMoveBinding
import com.example.moveapp.models.room_data_base.app_data_base.AppDatabase
import com.example.moveapp.models.room_data_base.entity.MoveNewPlayingEntity
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.ArrayList
import kotlin.coroutines.CoroutineContext

class TopMoveFragment : Fragment(R.layout.fragment_top_move), CoroutineScope {
    private val binding by viewBinding(FragmentTopMoveBinding::bind)

    private lateinit var appDatabase: AppDatabase
    private lateinit var adapterFavRv: AdapterFavRv
    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        appDatabase = AppDatabase.getInstance(requireContext())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDataView()
    }

    private fun setDataView() {

        launch {
            adapterFavRv = AdapterFavRv(
                appDatabase.moveDao().getMoveNewPlaying() as ArrayList<MoveNewPlayingEntity>
            ) {
                val bundle = Bundle()
                bundle.putInt("id_move_playing", it.id)
                findNavController().navigate(R.id.infoPageFragment, bundle)
            }
            binding.rv.adapter = adapterFavRv

        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onStart() {
        super.onStart()
        val mainActivity = activity as MainActivity
        mainActivity.viewVisiblite()
    }

}