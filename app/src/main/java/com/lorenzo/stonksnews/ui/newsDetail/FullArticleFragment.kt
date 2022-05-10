package com.lorenzo.stonksnews.ui.newsDetail

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.lorenzo.stonksnews.databinding.FragmentFullArticleBinding
import com.lorenzo.stonksnews.viewModel.NewsDetailViewModel


class FullArticleFragment : Fragment() {
    private var binding: FragmentFullArticleBinding? = null

    private val newsDetailViewModel by activityViewModels<NewsDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFullArticleBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.wvWebView?.settings?.javaScriptEnabled = true
        binding?.wvWebView?.webViewClient = MyWebViewClient()
        newsDetailViewModel.newsItem?.url?.let { binding?.wvWebView?.loadUrl(it) }
    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return false
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            binding?.pbProgress?.isVisible = false
        }
    }
}
