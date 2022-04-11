package com.example.moveapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.moveapp.R
import com.example.moveapp.databinding.ItemFavBinding
import com.example.moveapp.models.room_data_base.entity.MoveNewPlayingEntity
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
            Picasso.get().load(movePopularEntity.image_url).error(R.drawable.not_load_image)
                .into(homeItemBinding.imageFilms)
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

        val animationFadeIn = AnimationUtils.loadAnimation(
            holder.homeItemBinding.root.context,
            R.anim.alpha
        )
        holder.itemView.startAnimation(animationFadeIn)


        holder.itemView.setOnClickListener {
            itemClickListener.invoke(list.get(position))
        }
    }

    override fun getItemCount(): Int = list.size

}