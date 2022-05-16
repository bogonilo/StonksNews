package com.lorenzo.stonksnews.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lorenzo.stonksnews.databinding.ItemRegionBinding
import com.lorenzo.stonksnews.ui.viewHolder.RegionViewHolder

class RegionAdapter(onItemClickListener: OnClickListener<String>) :
    BaseAdapter<String, RegionViewHolder>(onItemClickListener) {
    private var selectedRegion: String? = null

    override fun compare(item1: String, item2: String): Int {
        return when {
            item1 == selectedRegion -> -1
            item2 == selectedRegion -> 1
            else -> super.compare(item1, item2)
        }
    }

    override fun onBindViewHolder(holder: RegionViewHolder, item: String) {
        holder.bindRegion(item, selectedRegion == item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val binding = ItemRegionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return RegionViewHolder(binding)
    }

    fun setNewSelectedRegion(region: String) {
        val oldSelectedRegionPosition = itemsPositionMap[selectedRegion] ?: -1
        selectedRegion = region
        itemsPositionMap[region]?.let { position ->
            notifyItemChanged(position)

            if (oldSelectedRegionPosition != -1) {
                notifyItemChanged(oldSelectedRegionPosition)
            }
        } ?: notifyDataSetChanged()
    }
}
