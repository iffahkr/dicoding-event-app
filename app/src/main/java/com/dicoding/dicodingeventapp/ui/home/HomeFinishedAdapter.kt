package com.dicoding.dicodingeventapp.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingeventapp.data.local.entity.FavoriteEvent
import com.dicoding.dicodingeventapp.databinding.ItemHomeFinishedBinding
import com.dicoding.dicodingeventapp.ui.detail.DetailEventActivity

class HomeFinishedAdapter(private val onItemClick: (FavoriteEvent) -> Unit) :
    ListAdapter<FavoriteEvent, HomeFinishedAdapter.ViewHolder>(DIFF_CALLBACK) {

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

    class ViewHolder(private val binding: ItemHomeFinishedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: FavoriteEvent, onItemClick: (FavoriteEvent) -> Unit) {
            binding.tvHomeNameFinished.text = event.name
            binding.tvHomeNameDesc.text = event.summary
            Glide.with(binding.ivHomeFinished.context)
                .load(event.mediaCover)
                .into(binding.ivHomeFinished)

            itemView.setOnClickListener {
                onItemClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeFinishedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event, onItemClick)
    }

}