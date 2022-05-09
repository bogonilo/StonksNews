package com.lorenzo.stonksnews.ui.newsDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.lorenzo.stonksnews.databinding.FragmentNewsDetailBinding
import com.lorenzo.stonksnews.viewModel.MainActivityViewModel

class NewsDetailFragment : Fragment() {
    private var binding: FragmentNewsDetailBinding? = null

    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        binding?.newsItem = mainActivityViewModel.newsItem

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.ivNewsThumbnail?.let {
            Glide.with(it.context)
                .load(mainActivityViewModel.newsItem?.image_url)
                .into(it)
        }
    }
}