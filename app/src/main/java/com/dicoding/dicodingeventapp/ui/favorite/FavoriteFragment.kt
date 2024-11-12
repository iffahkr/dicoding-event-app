package com.dicoding.dicodingeventapp.ui.favorite

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingeventapp.databinding.FragmentFavoriteBinding
import com.dicoding.dicodingeventapp.ui.ListEventAdapter
import com.dicoding.dicodingeventapp.ui.MainViewModel
import com.dicoding.dicodingeventapp.ui.ViewModelFactory

class FavoriteFragment : Fragment() {

    private lateinit var listEventAdapter: ListEventAdapter
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavoriteEvents().observe(viewLifecycleOwner) { favoriteEvents ->
            if (favoriteEvents != null) {
                viewModel.isLoading.observe(viewLifecycleOwner) {
                    showLoading(it)
                }
                listEventAdapter.submitList(arrayListOf(favoriteEvents))
            } else {
                viewModel.isLoading.observe(viewLifecycleOwner) {
                    showLoading(it)
                }
            }
        }

        binding.rvFavorite.layoutManager = LinearLayoutManager(requireActivity())
        listEventAdapter = ListEventAdapter()
        binding.rvFavorite.apply {
            adapter = listEventAdapter
            setHasFixedSize(true)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}