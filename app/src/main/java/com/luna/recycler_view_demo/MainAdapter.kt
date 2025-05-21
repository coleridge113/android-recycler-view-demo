package com.luna.recycler_view_demo

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luna.recycler_view_demo.databinding.ItemBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.ItemViewHolder>() {
    private val repository: Repository = Repository()
    private val items: List<String> = repository.getItems()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        holder.textView.text = items[position]
        Log.d("MainAdapter", items.toString())
    }

    override fun getItemCount(): Int = items.size

    class ItemViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView = binding.textView
    }
}