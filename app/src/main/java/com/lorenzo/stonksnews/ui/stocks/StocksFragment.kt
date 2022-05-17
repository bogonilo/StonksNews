package com.lorenzo.stonksnews.ui.stocks

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.lorenzo.stonksnews.databinding.FragmentStocksBinding
import com.lorenzo.stonksnews.ui.adapter.BaseAdapter
import com.lorenzo.stonksnews.ui.adapter.RegionAdapter
import com.lorenzo.stonksnews.ui.adapter.StockItemAdapter
import com.lorenzo.stonksnews.viewModel.NewsDetailViewModel
import com.lorenzo.stonksnews.viewModel.StocksViewModel

class StocksFragment : Fragment(), BaseAdapter.OnClickListener<String> {
    private var _binding: FragmentStocksBinding? = null

    private val binding
        get() = _binding
            ?: error("This property is only valid between onCreateView and onDestroyView.")

    private var recyclerViewScrollHandler: Handler? = Handler(Looper.getMainLooper())

    private val regionAdapter = RegionAdapter(this)

    private var selectedRegion: String = DEFAULT_SELECTED_REGION

    private val stocksItemAdapter = StockItemAdapter()

    private val stocksViewModel: StocksViewModel by lazy {
        val application = activity?.application
            ?: error("You can only access the viewModel after onActivityCreated()")

        ViewModelProvider(
            this,
            StocksViewModel.Factory(application)
        )[StocksViewModel::class.java]
    }

    companion object {
        private const val DEFAULT_SELECTED_REGION = "US"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStocksBinding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onItemClicked(item: String) {
        selectedRegion = item
        stocksItemAdapter.items = emptyList()
        stocksViewModel.saveUserRegionToPreferencesStore(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvRegion.adapter = regionAdapter
        binding.rvTrendingSymbols.adapter = stocksItemAdapter

        stocksViewModel.selectedRegion.observe(viewLifecycleOwner) {
            it?.let { region ->
                selectedRegion = region
            }

            binding.pbLoading.isVisible = true
            stocksViewModel.loadTrendingSymbols(selectedRegion)
            regionAdapter.setNewSelectedRegion(selectedRegion)
            regionAdapter.items = stocksViewModel.regions
            recyclerViewScrollHandler?.postDelayed({
                binding.rvRegion.scrollToPosition(0)
            }, 600)
        }

        stocksViewModel.trendingSymbols.observe(viewLifecycleOwner) { symbol ->
            val symbols = symbol?.quotes?.map { it.symbol } ?: return@observe
            stocksViewModel.loadStockValues(symbols)
        }

        stocksViewModel.stockHistory.observe(viewLifecycleOwner) {
            stocksItemAdapter.items = it ?: emptyList()
            binding.pbLoading.isVisible = it?.isEmpty() != false
        }

        stocksViewModel.errorLimitReached.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "API limit reached :(", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        recyclerViewScrollHandler = null
        _binding = null
    }
}