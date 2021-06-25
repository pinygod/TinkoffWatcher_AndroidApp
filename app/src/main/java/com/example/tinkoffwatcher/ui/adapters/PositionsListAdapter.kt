package com.example.tinkoffwatcher.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffwatcher.data.PositionSettings
import com.example.tinkoffwatcher.databinding.PositionRecyclerItemBinding

class PositionsListAdapter(private val listener: OnItemClickListener) :
    ListAdapter<PositionSettings, PositionsListAdapter.PositionViewHolder>(Companion) {

    class PositionViewHolder(val binding: PositionRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<PositionSettings>() {
        override fun areItemsTheSame(oldItem: PositionSettings, newItem: PositionSettings): Boolean =
            oldItem.position.figi == newItem.position.figi

        override fun areContentsTheSame(oldItem: PositionSettings, newItem: PositionSettings): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        val layouInflater = LayoutInflater.from(parent.context)
        val binding = PositionRecyclerItemBinding.inflate(layouInflater, parent, false)

        return PositionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) {
        val position = getItem(position)
        holder.binding.apply {
            this.position = position
            this.listener = this@PositionsListAdapter.listener
            executePendingBindings()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: PositionSettings)
    }
}