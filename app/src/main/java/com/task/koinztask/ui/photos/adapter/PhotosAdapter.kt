package com.task.koinztask.ui.photos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.koinztask.databinding.PhotoItemBinding
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.task.koinztask.R
import com.task.koinztask.ui.photos.PhotoVM

@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, url: String?) {
    Glide.with(view.context).load(url).placeholder(R.drawable.ic_launcher_background).into(view)
}

class PhotosAdapter constructor() :
    PagingDataAdapter<PhotoVM, PhotosAdapter.CharacterViewHolder>(CharacterComparator) {
    var characterClickListener: PhotoClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CharacterViewHolder(
            PhotoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class CharacterViewHolder(private val binding: PhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PhotoVM) = with(binding) {
            photo = item
        }
    }

    object CharacterComparator : DiffUtil.ItemCallback<PhotoVM>() {
        override fun areItemsTheSame(oldItem: PhotoVM, newItem: PhotoVM) =
            oldItem.imageUrl == newItem.imageUrl

        override fun areContentsTheSame(oldItem: PhotoVM, newItem: PhotoVM) =
            oldItem == newItem
    }
}

interface PhotoClickListener{

}