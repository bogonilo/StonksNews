package com.lorenzo.stonksnews.ui.preferences

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.lorenzo.stonksnews.databinding.FragmentPreferencesBinding
import com.lorenzo.stonksnews.model.FavoriteSymbol
import com.lorenzo.stonksnews.ui.adapter.BaseAdapter
import com.lorenzo.stonksnews.ui.adapter.FavoriteSymbolAdapter
import com.lorenzo.stonksnews.viewModel.PreferencesViewModel

class PreferencesFragment : Fragment(), BaseAdapter.OnClickListener<FavoriteSymbol> {
    private var _binding: FragmentPreferencesBinding? = null

    private val binding get() = _binding
        ?: error("This property is only valid between onCreateView and onDestroyView.")

    private val favoriteSymbolAdapter = FavoriteSymbolAdapter(this)

    private val viewModel: PreferencesViewModel by lazy {
        val application = activity?.application
            ?: error("You can only access the viewModel after onCreateView()")

        ViewModelProvider(
            this,
            PreferencesViewModel.Factory(application)
        )[PreferencesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreferencesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onItemClicked(item: FavoriteSymbol) {
        viewModel.removeFavorite(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        binding.rvFavoriteSymbols.layoutManager = layoutManager
        binding.rvFavoriteSymbols.adapter = favoriteSymbolAdapter
        viewModel.favoriteSymbols.observe(viewLifecycleOwner) {
            favoriteSymbolAdapter.items = it
            binding.scNewsTypeSelector.isVisible = it.isNotEmpty()
        }

        viewModel.filterNewsBySymbol.observe(viewLifecycleOwner) {
            binding.scNewsTypeSelector.isChecked = it
        }

        binding.tietSymbolsInput.setOnKeyListener(OnEnterKeyListener())
        binding.scNewsTypeSelector.setOnCheckedChangeListener(OnCheckListener())
    }

    private inner class OnCheckListener : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(view: CompoundButton?, checked: Boolean) {
            viewModel.setNewsFilterPreference(checked)
        }
    }

    private inner class OnEnterKeyListener : View.OnKeyListener {
        override fun onKey(view: View?, code: Int, event: KeyEvent?): Boolean {
            if (event?.action == KeyEvent.ACTION_DOWN) {
                when (code) {
                    KeyEvent.KEYCODE_DPAD_CENTER,
                    KeyEvent.KEYCODE_ENTER -> {
                        viewModel.insertFavoriteSymbol(binding.tietSymbolsInput.text.toString().uppercase())
                        binding.tietSymbolsInput.text?.clear()

                        return true
                    }
                    else -> {
                        // NO operations
                    }
                }
            }

            return false
        }
    }
}