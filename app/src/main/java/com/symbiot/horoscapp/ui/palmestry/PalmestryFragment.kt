package com.symbiot.horoscapp.ui.palmestry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.symbiot.horoscapp.databinding.FragmentPalmestryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PalmestryFragment : Fragment() {
    private var _binding: FragmentPalmestryBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPalmestryBinding.inflate(inflater, container, false)
        return binding.root
    }


}