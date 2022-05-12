package com.lorenzo.stonksnews.ui.viewHolder

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lorenzo.stonksnews.R
import com.lorenzo.stonksnews.databinding.ItemRegionBinding
import com.lorenzo.stonksnews.util.setBackgroundTint


class RegionViewHolder(val binding: ItemRegionBinding): RecyclerView.ViewHolder(binding.root)  {
    fun bindRegion(region: String, isSelected: Boolean) {
        binding.region = region

        if (isSelected) {
            binding.tvRegion.setBackgroundTint(R.color.purple_500)
            binding.tvRegion.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
        } else {
            binding.tvRegion.setBackgroundTint(R.color.white)
            binding.tvRegion.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
        }
    }
}
