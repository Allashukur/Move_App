package com.example.moveapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
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
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)

    private lateinit var networkHelper: NetworkHelper
    private lateinit var moveViewModel: MoveViewModel
    private lateinit var appDatabase: AppDatabase
    private lateinit var adapterViewPager: HomeViewSilderAdapter
    private lateinit var adaterRv: AdapterHomeRv
    private var tempList = ArrayList<MovePopularEntity>()
    private var newList = ArrayList<MovePopularEntity>()

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

        serchView()

        lifecycleScope.launch {
            moveViewModel.getMove().collect {
                when (it) {
                    is MoveResource.Error -> {
                    }
                    MoveResource.Loading -> {

                        binding.progress.visibility = View.VISIBLE
                    }
                    is MoveResource.Succes -> {
                        binding.progress.visibility = View.GONE
                        setDataView(it.list, it.list2)
                    }
                }
            }

        }

    }

    private fun serchView() {
        binding.toolbarLayout.apply {
            search.setOnSearchClickListener {
                titleToolbar.visibility = View.GONE
            }

            search.setOnCloseListener(object :
                android.widget.SearchView.OnCloseListener,
                SearchView.OnCloseListener {
                override fun onClose(): Boolean {

                    titleToolbar.visibility = View.VISIBLE
                    return false
                }

            })

            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {


                    tempList.clear()

                    val searchText = newText?.lowercase(Locale.getDefault())
                    if (searchText != null && searchText.isNotEmpty()) {
                        Log.d("moveApp", "${newList}")
                        newList.forEach {
                            if (it.title.lowercase(Locale.getDefault()).contains(searchText)) {
                                tempList.add(it)
                            }
                        }
                        binding.viewPager2.adapter?.notifyDataSetChanged()
                    } else {
                        tempList.clear()
                        tempList.addAll(newList)
                        binding.viewPager2.adapter?.notifyDataSetChanged()
                    }
//                    binding.viewPager2.adapter?.notifyDataSetChanged()
                    return false
                }

            })

        }

    }

    private fun setDataView(list: List<MovePopularEntity>, list2: List<MoveNewPlayingEntity>) {

        tempList = list as ArrayList<MovePopularEntity>
        newList.addAll(list)

        val navOptions: NavOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.enter)
            .setExitAnim(R.anim.exit)
            .setPopEnterAnim(R.anim.pop_enter)
            .setPopExitAnim(R.anim.pop_exit)
            .build()

        adapterViewPager = HomeViewSilderAdapter(tempList) {
            val bundle = Bundle()
            bundle.putInt("id_move_popular", it)
            findNavController().navigate(R.id.infoPageFragment, bundle, navOptions)
        }
        adaterRv = AdapterHomeRv(list2) {
            val bundle = Bundle()
            bundle.putInt("id_move_playing", it)
            findNavController().navigate(R.id.infoPageFragment, bundle, navOptions)
        }
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
        binding.viewPager2.adapter = adapterViewPager
        binding.homeRv.adapter = adaterRv
        binding.viewPager2.setPageTransformer(compositePageTransformer)
    }

    override fun onStart() {
        super.onStart()
        binding.toolbarLayout.apply {
            search.isIconified = true
            search.onActionViewCollapsed()
        }
        val mainActivity = activity as MainActivity
        mainActivity.viewVisiblite()
    }

}