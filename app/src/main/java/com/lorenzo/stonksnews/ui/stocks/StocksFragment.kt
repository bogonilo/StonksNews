package com.lorenzo.stonksnews.ui.stocks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lorenzo.stonksnews.databinding.FragmentStocksBinding
import com.lorenzo.stonksnews.ui.adapter.BaseAdapter
import com.lorenzo.stonksnews.ui.adapter.RegionAdapter

class StocksFragment : Fragment(), BaseAdapter.OnClickListener<String> {
    private var _binding: FragmentStocksBinding? = null

    private val binding
        get() = _binding
            ?: error("This property is only valid between onCreateView and onDestroyView.")

    private val regionAdapter = RegionAdapter(this)

    private val stocksViewModel by viewModels<StocksViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStocksBinding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onItemClicked(item: String) {
        regionAdapter.setNewSelectedRegion(item)
        stocksViewModel.loadTrendingSymbols(item)
        binding.rvRegion.smoothScrollToPosition(0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvRegion.adapter = regionAdapter

        stocksViewModel.selectedRegion.observe(viewLifecycleOwner) {
            it?.let { region -> regionAdapter.setNewSelectedRegion(region)}
            regionAdapter.items = stocksViewModel.regions
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}