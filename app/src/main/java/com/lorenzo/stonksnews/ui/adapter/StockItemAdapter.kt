package com.lorenzo.stonksnews.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lorenzo.stonksnews.databinding.ItemStockBinding
import com.lorenzo.stonksnews.model.yfapi.StockHistory
import com.lorenzo.stonksnews.ui.viewHolder.StocksItemViewHolder

class StockItemAdapter : BaseAdapter<StockHistory, StocksItemViewHolder>() {
    override fun onBindViewHolder(holder: StocksItemViewHolder, item: StockHistory) {
        holder.bindStock(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StocksItemViewHolder {
        val binding = ItemStockBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return StocksItemViewHolder(binding)
    }
}
