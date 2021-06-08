package com.example.tinkoffwatcher.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffwatcher.data.Stock
import com.example.tinkoffwatcher.databinding.StockRecyclerItemBinding

class StocksListAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Stock, StocksListAdapter.StockViewHolder>(Companion) {

    class StockViewHolder(val binding: StockRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<Stock>() {
        override fun areItemsTheSame(oldItem: Stock, newItem: Stock): Boolean =
            oldItem.figi == newItem.figi

        override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val layouInflater = LayoutInflater.from(parent.context)
        val binding = StockRecyclerItemBinding.inflate(layouInflater, parent, false)

        return StockViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = getItem(position)
        holder.binding.apply {
            this.stock = stock
            this.listener = this@StocksListAdapter.listener
            executePendingBindings()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(stock: Stock)
    }
}