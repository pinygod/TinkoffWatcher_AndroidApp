package com.example.tinkoffwatcher.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffwatcher.data.PositionSettings
import com.example.tinkoffwatcher.data.RecyclerObject
import com.example.tinkoffwatcher.data.RecyclerObjectType
import com.example.tinkoffwatcher.databinding.NonobservableRecyclerItemBinding
import com.example.tinkoffwatcher.databinding.PositionRecyclerItemBinding
import com.example.tinkoffwatcher.databinding.TitleRecyclerItemBinding

class PositionsListAdapter(private val itemClickListener: OnItemClickListener, private val observeChangeListener: OnObserveChangeListener) :
    ListAdapter<RecyclerObject, RecyclerView.ViewHolder>(Companion) {

    class PositionViewHolder(val binding: PositionRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class TitleViewHolder(val binding: TitleRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class NonObservablePositionViewHolder(val binding: NonobservableRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<RecyclerObject>() {
        override fun areItemsTheSame(oldItem: RecyclerObject, newItem: RecyclerObject): Boolean {
            return if (oldItem.type == newItem.type) {
                if (oldItem.type == RecyclerObjectType.Title && newItem.type == RecyclerObjectType.Title) {
                    oldItem.item as String == newItem.item as String
                } else {
                    (oldItem.item as PositionSettings).positionFigi == (newItem.item as PositionSettings).positionFigi
                }
            } else {
                false
            }
        }

        override fun areContentsTheSame(
            oldItem: RecyclerObject,
            newItem: RecyclerObject
        ): Boolean {
            return if (oldItem.type == newItem.type) {
                if (oldItem.type == RecyclerObjectType.Title && newItem.type == RecyclerObjectType.Title) {
                    oldItem.item as String == newItem.item as String
                } else {
                    oldItem.item as PositionSettings == newItem.item as PositionSettings
                }
            } else {
                false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            RecyclerObjectType.Title.value -> {
                val binding = TitleRecyclerItemBinding.inflate(layoutInflater, parent, false)
                TitleViewHolder(binding)
            }
            RecyclerObjectType.ObservablePosition.value -> {
                val binding = PositionRecyclerItemBinding.inflate(layoutInflater, parent, false)
                PositionViewHolder(binding)
            }
            else -> {
                val binding =
                    NonobservableRecyclerItemBinding.inflate(layoutInflater, parent, false)
                NonObservablePositionViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            RecyclerObjectType.Title.value -> {
                (holder as TitleViewHolder).binding.apply {
                    title = getItem(position).item as String
                }
            }
            RecyclerObjectType.ObservablePosition.value -> {
                val item = getItem(position).item as PositionSettings
                (holder as PositionViewHolder).binding.apply {
                    this.position = item
                    this.clickListener = this@PositionsListAdapter.itemClickListener
                    this.observeListener = this@PositionsListAdapter.observeChangeListener
                    executePendingBindings()
                }
            }
            else -> {
                val item = getItem(position).item as PositionSettings
                (holder as NonObservablePositionViewHolder).binding.apply {
                    this.position = item
                    this.observeListener = this@PositionsListAdapter.observeChangeListener
                    executePendingBindings()
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type.value
    }

    interface OnItemClickListener {
        fun onItemClick(position: PositionSettings)
    }

    interface OnObserveChangeListener {
        fun onObserveChange(position: PositionSettings)
    }
}