package com.lorenzo.stonksnews.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lorenzo.stonksnews.R
import com.lorenzo.stonksnews.databinding.FragmentHomeBinding
import com.lorenzo.stonksnews.databinding.ItemNewsBinding
import com.lorenzo.stonksnews.model.NewsItem
import com.lorenzo.stonksnews.ui.viewHolder.NewsItemViewHolder
import com.lorenzo.stonksnews.viewModel.NewsListViewModel

class HomeFragment : Fragment() {
    private val adapter = NewsListAdapter()

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: NewsListViewModel by lazy {
        val application = activity?.application
            ?: error("You can only access the viewModel after onActivityCreated()")

        ViewModelProvider(
            this,
            NewsListViewModel.Factory(application)
        )[NewsListViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvNews.adapter = adapter
        binding.rvNews.layoutManager = LinearLayoutManager(context)
        viewModel.allNews.observe(viewLifecycleOwner) {
            adapter.news = it
        }
    }

    private inner class NewsListAdapter : RecyclerView.Adapter<NewsItemViewHolder>() {
        var news = listOf<NewsItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
            val binding = ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return NewsItemViewHolder(binding)
        }

        override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
            holder.binding.newsItem = news[position]
            holder.bindNews(news[position])
        }

        override fun getItemCount() = news.size
    }
}