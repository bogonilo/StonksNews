package com.lorenzo.stonksnews.ui.newsDetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.lorenzo.stonksnews.databinding.FragmentFullArticleBinding
import com.lorenzo.stonksnews.util.isInternetConnected
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

    // I want javaScript to be enabled anyway, since the sources of the articles should be trustable
    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.wvWebView?.settings?.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            cacheMode = WebSettings.LOAD_NO_CACHE
        }
        binding?.wvWebView?.webViewClient = MyWebViewClient()
        loadFullArticle()
    }

    /*
    Since the rest of the news logic works well in absence of internet connection I decided to store
    locally also a version of the article body, that I get through a simple OkHTTP request.
    Obviously I don't get also all the dynamic resources linked to it (CSS, JS, images) so it just
    displays some unformatted HTML. This could be an improvement point
     */
    private fun loadFullArticle() {
        val articleUrl = newsDetailViewModel.newsItem?.url ?: return
        val articleBody = newsDetailViewModel.articleBody

        if (context?.isInternetConnected() == true) {
            binding?.wvWebView?.loadUrl(articleUrl)
        } else {
            articleBody?.observe(viewLifecycleOwner) {
                it?.let { articleBody ->
                    binding?.wvWebView?.loadDataWithBaseURL(
                        null,
                        articleBody.body,
                        "text/html",
                        "utf-8",
                        null
                    )
                } ?: run {
                    Toast.makeText(context, "Article was not cached", Toast.LENGTH_LONG).show()
                }
            }
        }
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
