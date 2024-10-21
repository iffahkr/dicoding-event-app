package com.dicoding.dicodingeventapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingeventapp.data.response.Event
import com.dicoding.dicodingeventapp.data.response.ListEventsItem
import com.dicoding.dicodingeventapp.databinding.ActivityDetailEventBinding
import com.dicoding.dicodingeventapp.databinding.ItemEventBinding

class ListEventAdapter : ListAdapter<ListEventsItem, ListEventAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
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
    }

    class MyViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            Glide.with(binding.ivEvent.context)
                .load(event.imageLogo)
                .into(binding.ivEvent)
            binding.tvEventTitle.text = event.name

            itemView.setOnClickListener{
                val intent = Intent(itemView.context, DetailEventActivity::class.java)
                intent.putExtra(DetailEventActivity.KEY_EVENT, event)
                itemView.context.startActivity(intent)
            }

        }
    }

    class EventViewHolder(private val binding: ActivityDetailEventBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindDetail(eventDetail: Event) {
            binding.tvDetailName.text = eventDetail.name
            binding.tvDetailSummary.text = eventDetail.summary
            binding.tvDetailQuota.text = eventDetail.quota.toString()
            binding.tvDetailStartTime.text = eventDetail.beginTime
            binding.tvInfo.text = "Information: "
            binding.tvOwnerName.text = eventDetail.ownerName
            binding.tvDetailDescription.text = HtmlCompat.fromHtml(
                eventDetail.description.toString(),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            Glide.with(binding.ivDetailLogo)
                .load(eventDetail.imageLogo)
                .into(binding.ivDetailLogo)

            binding.btnRegister.setOnClickListener {
                val url = eventDetail.link
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                itemView.context.startActivity((intent))
            }
        }
    }
}