package com.lorenzo.stonksnews.ui.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.lorenzo.stonksnews.databinding.ItemFavoriteSymbolBinding
import com.lorenzo.stonksnews.model.FavoriteSymbol

class FavoriteSymbolViewHolder(val binding: ItemFavoriteSymbolBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindSymbol(symbol: FavoriteSymbol) {
        binding.value = symbol.value
    }
}
