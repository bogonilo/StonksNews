package com.lorenzo.stonksnews.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lorenzo.stonksnews.databinding.ItemFavoriteSymbolBinding
import com.lorenzo.stonksnews.model.FavoriteSymbol
import com.lorenzo.stonksnews.ui.viewHolder.FavoriteSymbolViewHolder

class FavoriteSymbolAdapter(onClickListener: OnClickListener<FavoriteSymbol>) :
    BaseAdapter<FavoriteSymbol, FavoriteSymbolViewHolder>(onClickListener) {
    override fun onBindViewHolder(holder: FavoriteSymbolViewHolder, item: FavoriteSymbol) {
        holder.bindSymbol(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteSymbolViewHolder {
        val binding = ItemFavoriteSymbolBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return FavoriteSymbolViewHolder(binding)
    }
}
