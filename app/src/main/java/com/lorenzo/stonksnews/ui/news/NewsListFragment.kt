package com.lorenzo.stonksnews.ui.news

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lorenzo.stonksnews.databinding.FragmentHomeBinding
import com.lorenzo.stonksnews.model.FavoriteSymbol
import com.lorenzo.stonksnews.model.marketaux.NewsItem
import com.lorenzo.stonksnews.ui.activity.NewsDetailActivity
import com.lorenzo.stonksnews.ui.adapter.NewsListAdapter
import com.lorenzo.stonksnews.viewModel.NewsListViewModel

class NewsListFragment : Fragment(), NewsListAdapter.Listener {
    private val adapter = NewsListAdapter(this)

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding
        ?: error("This property is only valid between onCreateView and onDestroyView.")

    private var filterBySymbol = false

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

    override fun onNewsClicked(newsItem: NewsItem) {
        val context = context ?: return
        startActivity(NewsDetailActivity.newIntent(context, newsItem))
    }

    override fun onShareNewsClicked(newsItem: NewsItem) {
        shareNews(newsItem)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvNews.adapter = adapter
        binding.rvNews.layoutManager = LinearLayoutManager(context)

        viewModel.allNews.observe(viewLifecycleOwner) {
            adapter.items = it
            binding.pbLoading.isVisible = it.isEmpty()
        }

        viewModel.errorLimitReached.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "API limit reached :(", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.filterNewsBySymbol.observe(viewLifecycleOwner) { filterByFavorite ->
            filterBySymbol = filterByFavorite


            if (filterByFavorite) {
                viewModel.favoriteSymbols.observe(viewLifecycleOwner) {
                    adapter.items = emptyList()
                    binding.pbLoading.isVisible = true
                    viewModel.refreshNews(true, it)
                }
            } else {
                viewModel.refreshNews(false)
            }
        }
    }

    private fun shareNews(newsItem: NewsItem) {
        val share = Intent(Intent.ACTION_SEND)
        share.type = "text/plain"

        share.putExtra(Intent.EXTRA_SUBJECT, newsItem.title)
        share.putExtra(Intent.EXTRA_TEXT, newsItem.url)

        startActivity(Intent.createChooser(share, "Share"))
    }
}