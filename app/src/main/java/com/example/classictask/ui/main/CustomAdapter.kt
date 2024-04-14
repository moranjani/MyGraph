package com.example.classictask.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.classictask.databinding.ListElementBinding

class CustomAdapter(private var data: List<Int>) : RecyclerView.Adapter<CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ListElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(data[position].toString())
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateDataList(newData: List<Int>) {
        data = newData
    }
}

class CustomViewHolder(private val binding: ListElementBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(value: String) {
        binding.textView.text = value
    }
}