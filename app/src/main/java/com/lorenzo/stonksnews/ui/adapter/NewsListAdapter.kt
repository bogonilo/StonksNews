package com.lorenzo.stonksnews.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lorenzo.stonksnews.databinding.ItemNewsBinding
import com.lorenzo.stonksnews.model.marketaux.NewsItem
import com.lorenzo.stonksnews.ui.viewHolder.NewsItemViewHolder

class NewsListAdapter(private val listener: Listener) :
    BaseAdapter<NewsItem, NewsItemViewHolder>() {
    override fun compare(item1: NewsItem, item2: NewsItem): Int {
        return item2.timeStampMillis?.compareTo(item1.timeStampMillis ?: 0) ?: -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return NewsItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, item: NewsItem) {
        holder.binding.newsItem = item
        holder.binding.root.setOnClickListener { listener.onNewsClicked(item) }
        holder.binding.ivShare.setOnClickListener { listener.onShareNewsClicked(item) }
        holder.bindNews(item)
    }

    interface Listener {
        fun onNewsClicked(newsItem: NewsItem)

        fun onShareNewsClicked(newsItem: NewsItem)
    }
}
