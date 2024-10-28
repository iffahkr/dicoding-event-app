package com.dicoding.dicodingeventapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.dicoding.dicodingeventapp.R
import com.dicoding.dicodingeventapp.data.response.ListEventsItem
import com.dicoding.dicodingeventapp.databinding.ActivityDetailEventBinding

@Suppress("DEPRECATION")
class DetailEventActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailEventBinding
    private val mainViewModel by viewModels<MainViewModel>()


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

        val event = intent.getParcelableExtra<ListEventsItem>(KEY_EVENT)
        if (event != null) {
            displayDetail(event)
        }

        binding.btnRegister.setOnClickListener(this)

    }

    @SuppressLint("SetTextI18n")
    private fun displayDetail(event: ListEventsItem) {
        binding.tvDetailName.text = event.name
        binding.tvDetailSummary.text = event.summary
        binding.tvDetailQuota.text = event.quota.toString()
        binding.tvDetailStartTime.text = event.beginTime
        binding.tvOwnerName.text = event.ownerName
        binding.tvInfo.text = "Information: "
        binding.tvDetailDescription.text = HtmlCompat.fromHtml(
            event.description.toString(),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        Glide.with(this)
            .load(event.mediaCover)
            .into(binding.ivDetailLogo)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnRegister.id -> {
                val register = intent.getParcelableExtra<ListEventsItem>(KEY_EVENT)
                val url = register?.link
                url?.let {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                    startActivity(intent)
                }
            }
        }
    }


}