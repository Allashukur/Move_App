package com.example.moveapp.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.moveapp.MainActivity
import com.example.moveapp.R
import com.example.moveapp.databinding.FragmentInfoPageBinding
import com.example.moveapp.models.room_data_base.app_data_base.AppDatabase
import com.squareup.picasso.Picasso
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

import kotlin.coroutines.CoroutineContext
import kotlin.math.roundToInt
import kotlin.math.roundToLong


class InfoPageFragment : Fragment(R.layout.fragment_info_page), CoroutineScope {
    private val binding by viewBinding(FragmentInfoPageBinding::bind)

    lateinit var appDatabase: AppDatabase
    lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        getData()
    }

    private fun getData() {
        appDatabase = AppDatabase.getInstance(requireContext())
        job = Job()

        val data = arguments?.getInt("id_move_popular")
        val data2 = arguments?.getInt("id_move_playing")

        launch {
            val movePopular = data?.let { appDatabase.moveDao().getMovePopularId(it) }
            if (movePopular != null) {
                setData(
                    movePopular.image_url,
                    movePopular.title,
                    movePopular.description,
                    movePopular.release_date,
                    movePopular.rank,
                    movePopular.favrorite
                )
                binding.apply {
                    favrorit.setOnClickListener {
                        if (movePopular.favrorite == true) {
                            movePopular.favrorite = false
                            favrorit.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
//                            movePopular.let { appDatabase.moveDao().editMovePopular(it) }
                        } else {
                            movePopular.favrorite = true
                            favrorit.setImageResource(R.drawable.ic_baseline_bookmark_24)
                        }
                        movePopular.let { appDatabase.moveDao().editMovePopular(it) }
                        Log.d("moveApp", "$movePopular")
                    }
                }
            }

        }
        launch {
            val moveNewPlayingId = data2?.let { appDatabase.moveDao().getMoveNewPlayingId(it) }
            if (moveNewPlayingId != null) {
                setData(
                    moveNewPlayingId.image_url,
                    moveNewPlayingId.title,
                    moveNewPlayingId.description,
                    moveNewPlayingId.release_date,
                    moveNewPlayingId.rank,
                    moveNewPlayingId.favrorite
                )
                binding.apply {
                    favrorit.setOnClickListener {
                        if (moveNewPlayingId.favrorite == true) {
                            moveNewPlayingId.favrorite = false
                            favrorit.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
                        } else {
                            moveNewPlayingId.favrorite = true
                            favrorit.setImageResource(R.drawable.ic_baseline_bookmark_24)
                        }
                        moveNewPlayingId.let { appDatabase.moveDao().editNewPlaying(it) }
                    }
                }
            }

        }


    }

    private fun setData(
        imageUrl: String,
        toolbarTitle: String,
        description: String,
        release: String,
        rank: String,
        favourite: Boolean
    ) {
        binding.apply {
            Picasso.get().load(imageUrl).into(background)
            toolbarLayout.title = toolbarTitle
            desc.setText(description)
            releaseDate.setText(release)
            val roundToInt = rank.toDouble().roundToInt()
            progressCircular.progress = roundToInt.times(10) ?: 1
            rankTitle.setText(rank ?: "0")
            back.setOnClickListener {
                findNavController().popBackStack()
            }
            if (favourite == true) {
                favrorit.setImageResource(R.drawable.ic_baseline_bookmark_24)
            } else {
                favrorit.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
            }


        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = activity as MainActivity
        mainActivity.viewGone()

    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()

    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


}