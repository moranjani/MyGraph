package com.example.mygraph.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mygraph.databinding.FragmentMainBinding
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {


    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startRunning.setOnClickListener {
            startTheCode()
        }
        binding.stopRunning.setOnClickListener {
            viewModel.stopRunning()
        }





    }

    private fun startTheCode() {
        viewModel.startRunning()
        viewModel.data.observe(viewLifecycleOwner) {list ->
            println("float array is: $list")
            binding.sparkView.adapter = CustomSparkAdapter(list.toFloatArray())
        }
    }

}