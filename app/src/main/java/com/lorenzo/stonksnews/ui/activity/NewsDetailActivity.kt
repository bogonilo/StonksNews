package com.lorenzo.stonksnews.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.lorenzo.stonksnews.R
import com.lorenzo.stonksnews.databinding.ActivityNewsDetailBinding
import com.lorenzo.stonksnews.model.marketaux.NewsItem
import com.lorenzo.stonksnews.viewModel.NewsDetailViewModel
import com.lorenzo.stonksnews.viewModel.NewsListViewModel

class NewsDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsDetailBinding

    private val newsDetailViewModel: NewsDetailViewModel by lazy {
        ViewModelProvider(
            this,
            NewsDetailViewModel.Factory(application)
        )[NewsDetailViewModel::class.java]
    }

    companion object {
        private const val EXTRA_NEWS_ITEM = "NEWS_ITEM"

        fun newIntent(context: Context, newsItem: NewsItem): Intent {
            val intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra(EXTRA_NEWS_ITEM, newsItem)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (intent.getSerializableExtra(EXTRA_NEWS_ITEM) as? NewsItem)?.let {
            newsDetailViewModel.setNewsItemSelected(it)
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_news_detail)
                as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }
}
