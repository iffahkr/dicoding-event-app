package com.dicoding.dicodingeventapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.dicodingeventapp.data.response.ListEventsItem
import com.dicoding.dicodingeventapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var listEventAdapter: ListEventAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.upcomingEvent.observe(viewLifecycleOwner) { eventResponse ->
            eventResponse?.listEvents?.let { events ->
                if (events.isNotEmpty()) {
                    setEventData(events[0])
                }
            }
        }

        mainViewModel.finishedEvent.observe(viewLifecycleOwner) { eventResponse ->
            eventResponse?.listEvents?.let { events ->
                if (events.isNotEmpty()) {
                    setEventData(events[0])
                }
            }
        }

        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        setupRecyclerView()

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        binding.rvHome.layoutManager = LinearLayoutManager(requireActivity())
        listEventAdapter = ListEventAdapter()
        binding.rvHome.apply {
            adapter = listEventAdapter
            setHasFixedSize(true)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setEventData(events: ListEventsItem) {
        binding.tvMarkUpcoming.text = "Upcoming"
        binding.tvMarkFinished.text = "Finished"
        binding.tvHomeNameDesc.text = events.description
        binding.tvHomeNameFinished.text = events.name
        binding.tvHomeNameUpcoming.text = events.name
        Glide.with(this)
            .load(events.imageLogo)
            .into(binding.ivHomeUpcoming)
        Glide.with(this)
            .load(events.imageLogo)
            .into(binding.ivHomeFinished)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}