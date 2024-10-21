package com.dicoding.dicodingeventapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingeventapp.data.response.Event
import com.dicoding.dicodingeventapp.databinding.ActivityDetailEventBinding

class DetailAdapter(private val event: Event) : RecyclerView.Adapter<DetailAdapter.EventViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventViewHolder {
        val binding = ActivityDetailEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(event)
    }


    class EventViewHolder(private val binding: ActivityDetailEventBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(event: Event) {
            binding.tvDetailName.text = event.name
            binding.tvDetailSummary.text = event.summary
            binding.tvDetailQuota.text = event.quota.toString()
            binding.tvDetailStartTime.text = event.beginTime
            binding.tvInfo.text = "Information: "
            binding.tvOwnerName.text = event.ownerName
            binding.tvDetailDescription.text = HtmlCompat.fromHtml(
                event.description.toString(),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            Glide.with(binding.ivDetailLogo)
                .load(event.imageLogo)
                .into(binding.ivDetailLogo)

            binding.btnRegister.setOnClickListener {
                val url = event.link
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                itemView.context.startActivity((intent))
            }
        }
    }
}