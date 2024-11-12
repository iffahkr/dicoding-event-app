package com.dicoding.dicodingeventapp.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.dicoding.dicodingeventapp.R
import com.dicoding.dicodingeventapp.data.local.entity.FavoriteEvent
import com.dicoding.dicodingeventapp.data.remote.response.Event
import com.dicoding.dicodingeventapp.databinding.ActivityDetailEventBinding
import com.dicoding.dicodingeventapp.ui.ViewModelFactory

@Suppress("DEPRECATION")
class DetailEventActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailEventBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private var event: Event? = null

    companion object {
        const val KEY_EVENT = "key_event"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        event = intent.getParcelableExtra(KEY_EVENT) as? Event
        val favoriteEvent = intent.getParcelableExtra(KEY_EVENT) as? FavoriteEvent

        event?.let {
            displayDetail(it)
        }

        if (event != null) {
            detailViewModel.getDetailEvent("")
        } else {
            Log.e("DetailEventActivity", "Event is null")
        }


//        favoriteEvent = intent.getParcelableExtra(KEY_EVENT) ?: return

        detailViewModel.isFavorite.observe(this) {
            updateFavoriteButton(it)
        }

        detailViewModel.detailEvent.observe(this) { event ->
            if (event != null) {
                displayDetail(event)
            }
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.btnRegister.setOnClickListener(this)

        binding.fabFavorite.setOnClickListener {
            detailViewModel.detailEvent.value?.let { event ->
                detailViewModel.toggleFavorite(event)
            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        val ivFavorite = binding.fabFavorite
        if (isFavorite) {
            ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    ivFavorite.context,
                    R.drawable.ic_favorite
                )
            )
        } else {
            ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    ivFavorite.context,
                    R.drawable.ic_unfavorite
                )
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayDetail(event: Event) {
        binding.tvDetailName.text = event.name
        binding.tvDetailSummary.text = event.summary
        binding.tvDetailQuota.text = quotaEvent(event)
        binding.tvDetailStartTime.text = event.beginTime
        binding.tvOwnerName.text = event.ownerName
        binding.tvInfo.text = "Information: "
        binding.tvDetailDescription.text = HtmlCompat.fromHtml(
            event.description,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        Glide.with(this)
            .load(event.mediaCover)
            .into(binding.ivDetailLogo)
    }

    private fun quotaEvent(event: Event) = (event.quota - event.registrants).toString()

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnRegister.id -> {
                val register = event
                val url = register?.link
                url?.let {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                    startActivity(intent)
                }
            }
        }
    }

    private fun Event.toFavoriteEvent(): FavoriteEvent {
        return FavoriteEvent(
            id = this.id.toString(),
            name = this.name,
            mediaCover = this.mediaCover,
            summary = this.summary,
            beginTime = this.beginTime,
            ownerName = this.ownerName,
            description = this.description,
            quota = this.quota,
            registrants = this.registrants,
            isFavorite = false
        )
    }

}