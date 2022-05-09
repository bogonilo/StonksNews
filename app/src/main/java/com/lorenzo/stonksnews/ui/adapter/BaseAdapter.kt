package com.lorenzo.stonksnews.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.lorenzo.stonksnews.ui.viewHolder.NewsItemViewHolder
import java.util.*

abstract class BaseAdapter<I: Any, V: RecyclerView.ViewHolder>(
    private val onClickListener: OnClickListener<I>? = null
) : RecyclerView.Adapter<V>(), Comparator<I> {
    var items = listOf<I>()
        set(value) {
            field = value.sortedWith(this).reversed()
            notifyDataSetChanged()
        }

    override fun compare(item1: I, item2: I): Int {
        return 0
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: V, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener { onClickListener?.onItemClicked(item) }
        onBindViewHolder(holder, item)
    }

    abstract fun onBindViewHolder(holder: V, item: I)

    interface OnClickListener<I> {
        fun onItemClicked(item: I)
    }
}