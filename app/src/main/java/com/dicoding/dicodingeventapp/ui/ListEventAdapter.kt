package com.dicoding.dicodingeventapp.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingeventapp.data.local.entity.FavoriteEvent
import com.dicoding.dicodingeventapp.databinding.ItemEventBinding
import com.dicoding.dicodingeventapp.ui.detail.DetailEventActivity

class ListEventAdapter : ListAdapter<FavoriteEvent, ListEventAdapter.MyViewHolder>(DIFF_CALLBACK) {

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
        val favoriteEvent = getItem(position)
        holder.bind(favoriteEvent)

    }

    class MyViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: FavoriteEvent) {
            Glide.with(binding.ivEvent.context)
                .load(event.mediaCover)
                .into(binding.ivEvent)
            binding.tvEventTitle.text = event.name

            itemView.setOnClickListener{
                val intent = Intent(itemView.context, DetailEventActivity::class.java)
                intent.putExtra(DetailEventActivity.KEY_EVENT, event)
                itemView.context.startActivity(intent)
            }

        }
    }
}