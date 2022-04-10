package com.example.moveapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.moveapp.MainActivity
import com.example.moveapp.R
import com.example.moveapp.adapter.AdapterFavRv
import com.example.moveapp.databinding.FragmentFavouriteBinding
import com.example.moveapp.databinding.FragmentHomeBinding
import com.example.moveapp.models.room_data_base.app_data_base.AppDatabase
import com.example.moveapp.models.room_data_base.entity.MoveNewPlayingEntity
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class favouriteFragment : Fragment(R.layout.fragment_favourite), CoroutineScope {
    private val binding by viewBinding(FragmentFavouriteBinding::bind)

    private lateinit var job: Job
    private lateinit var adapter: AdapterFavRv
    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()

    }

    private fun loadData() {
        var list = ArrayList<MoveNewPlayingEntity>()
        appDatabase = AppDatabase.getInstance(requireContext())
        job = Job()

        launch {
            list =
                appDatabase.moveDao().getNewMovePlayingFav(true) as ArrayList<MoveNewPlayingEntity>
            val movePopularFav = appDatabase.moveDao().getMovePopularFav(true)

            movePopularFav.forEach {
                list.add(
                    MoveNewPlayingEntity(
                        it.id,
                        it.title,
                        it.description,
                        it.image_url,
                        it.release_date,
                        it.rank, it.favrorite
                    )
                )
            }

            adapter = AdapterFavRv(list) {
                val bundle = Bundle()
                bundle.putSerializable("favrorit", it)
                findNavController().navigate(R.id.infoPageFragment, bundle)
            }
            binding.rv.adapter = adapter
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