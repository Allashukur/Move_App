package com.example.moveapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavOptions
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
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class TopMoveFragment : Fragment(R.layout.fragment_top_move), CoroutineScope {
    private val binding by viewBinding(FragmentTopMoveBinding::bind)

    private lateinit var appDatabase: AppDatabase
    private lateinit var adapterFavRv: AdapterFavRv
    private lateinit var job: Job
    private var tempList = ArrayList<MoveNewPlayingEntity>()
    private var newList = ArrayList<MoveNewPlayingEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        appDatabase = AppDatabase.getInstance(requireContext())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        serchView()
        setDataView()
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
                        newList.forEach {
                            if (it.title.lowercase(Locale.getDefault()).contains(searchText)) {
                                tempList.add(it)
                            }
                        }
                    } else {
                        tempList.clear()
                        tempList.addAll(newList)
                        binding.rv.adapter?.notifyDataSetChanged()
                    }
                    binding.rv.adapter?.notifyDataSetChanged()
                    return false
                }

            })
        }
    }


    private fun setDataView() {
        val navOptions: NavOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.enter)
            .setExitAnim(R.anim.exit)
            .setPopEnterAnim(R.anim.pop_enter)
            .setPopExitAnim(R.anim.pop_exit)
            .build()

        launch {
            tempList = appDatabase.moveDao().getMoveNewPlaying() as ArrayList<MoveNewPlayingEntity>
            newList.addAll(tempList)
            adapterFavRv = AdapterFavRv(
                tempList
            ) {
                val bundle = Bundle()
                bundle.putInt("id_move_playing", it.id)
                findNavController().navigate(R.id.infoPageFragment, bundle, navOptions)
            }
            binding.rv.adapter = adapterFavRv
            if (tempList.isNotEmpty()) {
                binding.progress.visibility = View.GONE
            }

        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


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