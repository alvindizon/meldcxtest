package com.alvindizon.meldcxtest.features.history

import android.content.Context
import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alvindizon.meldcxtest.R
import com.alvindizon.meldcxtest.databinding.ItemHistoryBinding
import com.bumptech.glide.Glide
import java.io.File


class HistoryUIItem(
    var url: String,
    var dateTime: String,
    var fileName: String) {
    override fun toString(): String {
        return "HistoryUIItem(url='$url', dateTime='$dateTime', fileName='$fileName')"
    }
}

class HistoryUIItemDiffCallback: DiffUtil.ItemCallback<HistoryUIItem>() {
    override fun areItemsTheSame(oldItem: HistoryUIItem, newItem: HistoryUIItem): Boolean {
        return oldItem.fileName == newItem.fileName
    }

    override fun areContentsTheSame(oldItem: HistoryUIItem, newItem: HistoryUIItem): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}

class HistoryAdapter(val listener: (HistoryUIItem) -> Unit, val viewModel: HistoryViewModel)
    : ListAdapter<HistoryUIItem, HistoryAdapter.ViewHolder>(HistoryUIItemDiffCallback()) {

    inner class ViewHolder(private val binding: ItemHistoryBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HistoryUIItem) {
            binding.model = item
            binding.viewModel = viewModel

            Glide.with(binding.image)
                .load(File(itemView.context.getDir(Environment.DIRECTORY_PICTURES, Context.MODE_PRIVATE), item.fileName))
                .thumbnail(0.33f)
                .centerCrop()
                .into(binding.image)
        }
    }

    // note: the value for viewType here comes from getItemViewType
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemHistoryBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { listener(getItem(position)) }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_history
    }
}