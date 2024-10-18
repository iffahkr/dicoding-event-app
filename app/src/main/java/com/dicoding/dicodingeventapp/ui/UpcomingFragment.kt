package com.dicoding.dicodingeventapp.ui

import androidx.fragment.app.Fragment
import com.dicoding.dicodingeventapp.databinding.FragmentUpcomingBinding

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        val upcomingViewModel =
//            ViewModelProvider(this)[UpcomingViewModel::class.java]
//
//        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        val textView: TextView = binding.tvUpcoming
//        upcomingViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
//        return root
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}