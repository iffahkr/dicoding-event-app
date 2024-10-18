package com.dicoding.dicodingeventapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingeventapp.data.response.EventResponse
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
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.upcomingEvent.observe(viewLifecycleOwner) { eventResponse ->
            setEventData(eventResponse)
        }

        mainViewModel.finishedEvent.observe(viewLifecycleOwner) { eventResponse ->
            setEventData(eventResponse)
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

    private fun setEventData(eventResponse: EventResponse) {
        eventResponse.listEvents.let {
            listEventAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}