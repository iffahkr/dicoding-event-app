package com.dicoding.dicodingeventapp.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingeventapp.data.local.entity.FavoriteEvent
import com.dicoding.dicodingeventapp.databinding.ItemHomeUpcomingBinding
import com.dicoding.dicodingeventapp.ui.detail.DetailEventActivity

class HomeUpcomingAdapter(private val onItemClick: (FavoriteEvent) -> Unit) :
    ListAdapter<FavoriteEvent, HomeUpcomingAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteEvent>() {
            override fun areItemsTheSame(
                oldItem: FavoriteEvent,
                newItem: FavoriteEvent
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FavoriteEvent,
                newItem: FavoriteEvent
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(private val binding: ItemHomeUpcomingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: FavoriteEvent, onItemClick: (FavoriteEvent) -> Unit) {
            binding.tvHomeNameUpcoming.text = event.name
            Glide.with(binding.ivHomeUpcoming.context)
                .load(event.mediaCover)
                .into(binding.ivHomeUpcoming)

            itemView.setOnClickListener {
                onItemClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event, onItemClick)
    }
}