package com.example.tinkoffwatcher.ui.stocks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tinkoffwatcher.data.Stock
import com.example.tinkoffwatcher.databinding.FragmentStockSettingsBinding
import com.example.tinkoffwatcher.viewmodels.StockSettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class StockSettingsFragment : Fragment() {

    private lateinit var binding: FragmentStockSettingsBinding
    private lateinit var stock: Stock
    private val viewModel by viewModel<StockSettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            stock = StockSettingsFragmentArgs.fromBundle(it).stock
            viewModel.setStock(stock)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStockSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            stock = this@StockSettingsFragment.stock
            viewmodel = viewModel
        }
    }
}