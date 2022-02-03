package com.example.interiewprepandroidapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.interiewprepandroidapp.data.model.SearchItem
import com.example.interiewprepandroidapp.databinding.SearchListItemBinding

interface SearchItemsListCallback {
    fun onItemSelected(searchItem : SearchItem, index : Int)
}

class SearchItemsAdapter(private val callback : SearchItemsListCallback) : ListAdapter<SearchItem, SearchItemViewHolder>(DIFF_CHECKER) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        return SearchItemViewHolder.createViaBinding(parent)
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        val searchItem = getItem(position)
        holder.setup(searchItem)

        holder.binding.root.setOnClickListener {
            val item = getItem(holder.layoutPosition)
            callback.onItemSelected(item, position)
        }
    }

    companion object {
        val DIFF_CHECKER = object : DiffUtil.ItemCallback<SearchItem>() {
            override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return oldItem.id == newItem.id && oldItem.value == newItem.value
            }
        }
    }
}

class SearchItemViewHolder(val binding : SearchListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setup(searchItem: SearchItem) {
        binding.searchItemText.text = searchItem.value
    }

    companion object {
        // how to create via binding
        fun createViaBinding(parent : ViewGroup) : SearchItemViewHolder {
            val binding = SearchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SearchItemViewHolder(binding)
        }


//        fun create(parent: ViewGroup): SearchItemViewHolder {
//            val view = LayoutInflater.from(parent.context).inflate(R.layout.search_list_item, parent, false)
//            return SearchItemViewHolder(view)
//        }
    }

}