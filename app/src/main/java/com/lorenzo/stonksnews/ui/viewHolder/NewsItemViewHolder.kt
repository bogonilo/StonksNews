package com.lorenzo.stonksnews.ui.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lorenzo.stonksnews.databinding.ItemNewsBinding
import com.lorenzo.stonksnews.model.marketaux.NewsItem

class NewsItemViewHolder(val binding: ItemNewsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindNews(item: NewsItem) {
        Glide.with(binding.ivNewsThumbnail.context)
            .load(item.image_url)
            .into(binding.ivNewsThumbnail)
    }
}