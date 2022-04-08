package com.example.moveapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moveapp.databinding.HomeItemBinding
import com.example.moveapp.models.room_data_base.entity.MoveNewPlayingEntity
import com.example.moveapp.models.room_data_base.entity.MovePopularEntity
import com.squareup.picasso.Picasso

class AdapterHomeRv(var list: List<MoveNewPlayingEntity>) :
    RecyclerView.Adapter<AdapterHomeRv.ViewHolder>() {

    inner class ViewHolder(val homeItemBinding: HomeItemBinding) :
        RecyclerView.ViewHolder(homeItemBinding.root) {

        fun onBind(movePopularEntity: MovePopularEntity) {
            Picasso.get().load(movePopularEntity.image_url).into(homeItemBinding.imageItem)
            homeItemBinding.apply {
                tagStateDescription.setText(movePopularEntity.description)
                moveTitle.setText(movePopularEntity.title)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HomeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.onBind(list.get(position))
    }

    override fun getItemCount(): Int = list.size

}