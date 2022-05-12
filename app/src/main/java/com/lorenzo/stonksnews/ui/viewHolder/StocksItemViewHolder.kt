package com.lorenzo.stonksnews.ui.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.lorenzo.stonksnews.databinding.ItemStockBinding
import com.lorenzo.stonksnews.model.yfapi.StockHistory

class StocksItemViewHolder(val binding: ItemStockBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindStock(item: StockHistory) {
        binding.stockItem = item
    }
}
