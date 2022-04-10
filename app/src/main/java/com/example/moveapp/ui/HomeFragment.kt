package com.example.moveapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.moveapp.MainActivity
import com.example.moveapp.R
import com.example.moveapp.adapter.AdapterHomeRv
import com.example.moveapp.adapter.HomeViewSilderAdapter
import com.example.moveapp.databinding.FragmentHomeBinding
import com.example.moveapp.models.retrofit.ApiClient
import com.example.moveapp.models.room_data_base.app_data_base.AppDatabase
import com.example.moveapp.models.room_data_base.entity.MoveNewPlayingEntity
import com.example.moveapp.models.room_data_base.entity.MovePopularEntity
import com.example.moveapp.resource.MoveResource
import com.example.moveapp.vm.MoveViewModel
import com.example.moveapp.vm.MoveViewModelFactory
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.mobiler.mvvmg23.utils.NetworkHelper

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)

    private lateinit var networkHelper: NetworkHelper
    private lateinit var moveViewModel: MoveViewModel
    private lateinit var appDatabase: AppDatabase
    private lateinit var adapterViewPager: HomeViewSilderAdapter
    private lateinit var adaterRv: AdapterHomeRv

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appDatabase = AppDatabase.getInstance(requireContext())
        networkHelper = NetworkHelper(requireContext())
        moveViewModel =
            ViewModelProvider(
                this,
                MoveViewModelFactory(networkHelper, ApiClient.apiServis, appDatabase)
            )[MoveViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            moveViewModel.getMove().collect {
                when (it) {
                    is MoveResource.Error -> {
                        Log.d("MOVE_APP", "${it.message}")
                    }
                    MoveResource.Loading -> {
                        Log.d("MOVE_APP", "${it}")
                    }
                    is MoveResource.Succes -> {
                        setDataView(it.list, it.list2)
                        Log.d("MOVE_APP", "${it}")
                    }
                }
            }

        }


    }

    private fun setDataView(list: List<MovePopularEntity>, list2: List<MoveNewPlayingEntity>) {
        adapterViewPager = HomeViewSilderAdapter(list) {
            val bundle = Bundle()
            bundle.putInt("id_move_popular", it)
            findNavController().navigate(R.id.infoPageFragment, bundle)
        }
        adaterRv = AdapterHomeRv(list2) {
            val bundle = Bundle()
            bundle.putInt("id_move_playing", it)
            findNavController().navigate(R.id.infoPageFragment, bundle)
        }
        binding.viewPager2.adapter = adapterViewPager
        binding.apply {
            viewPager2.clipToPadding = false
            viewPager2.clipChildren = false
            viewPager2.offscreenPageLimit = 3
            viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        var compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer(object : ViewPager2.PageTransformer {
            override fun transformPage(page: View, position: Float) {
                val r = 1 - Math.abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }

        })
        binding.homeRv.adapter = adaterRv
        binding.viewPager2.setPageTransformer(compositePageTransformer)
    }

    override fun onStart() {
        super.onStart()
        val mainActivity = activity as MainActivity
        mainActivity.viewVisiblite()
    }

}