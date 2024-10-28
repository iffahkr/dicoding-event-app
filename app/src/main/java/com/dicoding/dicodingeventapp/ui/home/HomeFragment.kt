package com.dicoding.dicodingeventapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingeventapp.databinding.FragmentHomeBinding
import com.dicoding.dicodingeventapp.ui.DetailEventActivity
import com.dicoding.dicodingeventapp.ui.MainViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var homeUpcomingAdapter: HomeUpcomingAdapter
    private lateinit var homeFinishedAdapter: HomeFinishedAdapter

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

        // Upcoming
        homeUpcomingAdapter = HomeUpcomingAdapter{
            val intent = Intent(requireContext(), DetailEventActivity::class.java)
            intent.putExtra("id", it.id)
            startActivity(intent)
        }

        binding.rvHomeUpcoming.adapter
        binding.rvHomeUpcoming.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = homeUpcomingAdapter
        }

        mainViewModel.upcomingEvent.observe(viewLifecycleOwner) {
            val list = mainViewModel.upcomingEvent.value
            homeUpcomingAdapter.submitList(list?.take(5))
        }

        mainViewModel.isLoadingUpcoming.observe(viewLifecycleOwner) {
            showLoadingUpcoming(it)
        }

        // Finished
        homeFinishedAdapter = HomeFinishedAdapter {
            val intent = Intent(requireContext(), DetailEventActivity::class.java)
            intent.putExtra("id", it.id)
            startActivity(intent)
        }

        binding.rvHomeFinished.adapter
        binding.rvHomeFinished.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = homeFinishedAdapter
        }

        mainViewModel.finishedEvent.observe(viewLifecycleOwner) {
            val list = mainViewModel.finishedEvent.value
            homeFinishedAdapter.submitList(list?.take(5))
        }

        mainViewModel.isLoadingFinished.observe(viewLifecycleOwner) {
            showLoadingFinished(it)
        }

    }

    private fun showLoadingUpcoming(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarUpcoming.visibility = View.VISIBLE
        } else {
            binding.progressBarUpcoming.visibility = View.GONE
        }
    }

    private fun showLoadingFinished(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarFinished.visibility = View.VISIBLE
        } else {
            binding.progressBarFinished.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}