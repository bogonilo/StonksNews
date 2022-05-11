package com.lorenzo.stonksnews.ui.stocks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lorenzo.stonksnews.databinding.FragmentStocksBinding

class StocksFragment : Fragment() {
    private var _binding: FragmentStocksBinding? = null

    private val binding get() = _binding
        ?: error("This property is only valid between onCreateView and onDestroyView.")

    private val stocksViewModel by viewModels<StocksViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStocksBinding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stocksViewModel.loadTrendingSymbols()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}