package com.example.moveapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moveapp.databinding.HomeItemBinding
import com.example.moveapp.databinding.ItemFavBinding
import com.example.moveapp.databinding.ItemHomeRvBinding
import com.example.moveapp.models.room_data_base.entity.MoveNewPlayingEntity
import com.example.moveapp.models.room_data_base.entity.MovePopularEntity
import com.squareup.picasso.Picasso
import java.util.ArrayList

class AdapterFavRv(
    var list: ArrayList<MoveNewPlayingEntity>,
    val itemClickListener: (MoveNewPlayingEntity) -> Unit
) :
    RecyclerView.Adapter<AdapterFavRv.ViewHolder>() {

    inner class ViewHolder(val homeItemBinding: ItemFavBinding) :
        RecyclerView.ViewHolder(homeItemBinding.root) {

        fun onBind(movePopularEntity: MoveNewPlayingEntity) {
            Picasso.get().load(movePopularEntity.image_url).into(homeItemBinding.imageFilms)
            homeItemBinding.apply {
                filmInfo.setText(movePopularEntity.description)
                filmName.setText(movePopularEntity.title)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFavBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list.get(position))

        holder.itemView.setOnClickListener {
            itemClickListener.invoke(list.get(position))
        }
    }

    override fun getItemCount(): Int = list.size

}