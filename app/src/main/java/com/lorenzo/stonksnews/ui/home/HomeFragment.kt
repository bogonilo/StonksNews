package com.lorenzo.stonksnews.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lorenzo.stonksnews.databinding.FragmentHomeBinding
import com.lorenzo.stonksnews.model.NewsItem
import com.lorenzo.stonksnews.ui.activity.NewsDetailActivity
import com.lorenzo.stonksnews.ui.adapter.BaseAdapter
import com.lorenzo.stonksnews.ui.adapter.NewsListAdapter
import com.lorenzo.stonksnews.viewModel.NewsDetailViewModel
import com.lorenzo.stonksnews.viewModel.NewsListViewModel

class HomeFragment : Fragment(), BaseAdapter.OnClickListener<NewsItem> {
    private val adapter = NewsListAdapter(this)

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val mainActivityViewModel by activityViewModels<NewsDetailViewModel>()

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
            adapter.items = it
        }
    }

    override fun onItemClicked(item: NewsItem) {
        val context = context ?: return
        startActivity(NewsDetailActivity.newIntent(context, item))
    }
}