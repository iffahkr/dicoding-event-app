package com.dicoding.dicodingeventapp.ui

import androidx.fragment.app.Fragment
import com.dicoding.dicodingeventapp.databinding.FragmentFinishedBinding

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        val dashboardViewModel =
//            ViewModelProvider(this).get(FinishedViewModel::class.java)
//
//        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
//        return root
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}