package com.example.firebasesocial.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasesocial.database.Post
import com.example.firebasesocial.databinding.ListItemBinding

class PhotoAdapter() : ListAdapter<Post, PhotoAdapter.ViewHolder>(SleepNightDiffCallback()) {

    class ViewHolder private constructor(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Post) {
            binding.post = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food=getItem(position)!!



        holder.bind(food)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


}




class SleepNightDiffCallback : DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.ImageUrl == newItem.ImageUrl
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}
