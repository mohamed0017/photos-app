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
import com.task.koinztask.databinding.AdBannerItemBinding
import com.task.koinztask.ui.photos.PhotoVM
import com.task.koinztask.ui.photos.UiModel

@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, url: String?) {
    Glide.with(view.context).load(url).placeholder(R.drawable.placeholder).into(view)
}

class PhotosAdapter : PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(UiModelComparator) {
    var characterClickListener: PhotoClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = when (viewType) {
        R.layout.photo_item -> PhotoViewHolder(
            PhotoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        else -> AdBannerViewHolder(
            AdBannerItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return when (peek(position)) {
            is UiModel.PhotoModel -> R.layout.photo_item
            is UiModel.AdBannerModel -> R.layout.ad_banner_item
            null -> throw IllegalStateException("Unknown view")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        if (holder is PhotoViewHolder)
            item?.let { holder.bind(it as UiModel.PhotoModel) }
        else if (holder is AdBannerViewHolder)
            item?.let { holder.bind(it as UiModel.AdBannerModel) }
    }

    inner class PhotoViewHolder(private val binding: PhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UiModel.PhotoModel) = with(binding) {
            photo = item.photoVM
        }
    }

    inner class AdBannerViewHolder(private val binding: AdBannerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UiModel.AdBannerModel) = with(binding) {
            imageUrl = item.adImageUrl
        }
    }

    object UiModelComparator : DiffUtil.ItemCallback<UiModel>() {
        override fun areItemsTheSame(
            oldItem: UiModel,
            newItem: UiModel
        ): Boolean {
            val isSameRepoItem = oldItem is UiModel.PhotoModel
                    && newItem is UiModel.PhotoModel
                    && oldItem.photoVM.imageUrl == newItem.photoVM.imageUrl

            val isSameSeparatorItem = oldItem is UiModel.AdBannerModel
                    && newItem is UiModel.AdBannerModel
                    && oldItem.adImageUrl == newItem.adImageUrl

            return isSameRepoItem || isSameSeparatorItem
        }

        override fun areContentsTheSame(
            oldItem: UiModel,
            newItem: UiModel
        ) = oldItem == newItem
    }
}

interface PhotoClickListener {

}