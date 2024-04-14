package com.example.classictask.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.classictask.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {


    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: CustomAdapter
    private val viewModel: MainViewModel by viewModels()
    private var dataList: List<Int> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addElement.setOnClickListener {
            addElement()
        }
        binding.stopRunning.setOnClickListener {
            viewModel.stopRunning()
        }

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        recyclerView.adapter = CustomAdapter(dataList)
        adapter = recyclerView.adapter as CustomAdapter
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            (recyclerView.layoutManager as LinearLayoutManager).orientation
        )
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        viewModel.dataByClick.observe(viewLifecycleOwner) {list ->
            adapter.updateDataList(list)
            adapter.notifyDataSetChanged()
        }

    }

    private fun addElement() {
        viewModel.onAddNumberClicked()

    }

}