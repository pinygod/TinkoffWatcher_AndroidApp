package com.example.tinkoffwatcher.ui.stocks

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tinkoffwatcher.adapters.StocksListAdapter
import com.example.tinkoffwatcher.data.Stock
import com.example.tinkoffwatcher.databinding.FragmentStocksListBinding
import com.example.tinkoffwatcher.ui.login.LoginActivity
import com.example.tinkoffwatcher.utils.StocksEvent
import com.example.tinkoffwatcher.utils.showMessage
import com.example.tinkoffwatcher.viewmodels.StocksListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class StocksListFragment : Fragment(), StocksListAdapter.OnItemClickListener {
    private lateinit var binding: FragmentStocksListBinding
    private val viewModel by viewModel<StocksListViewModel>()
    private val recyclerAdapter = StocksListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStocksListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            stocksRecycler.adapter = recyclerAdapter
            viewmodel = viewModel
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stocksEvent.collect { event ->
                    when (event) {
                        StocksEvent.NavigateToLogin -> navigateToLogin()
                        is StocksEvent.Loaded -> recyclerAdapter.submitList(event.stocks)
                        is StocksEvent.ShowMessage -> showMessage(binding.root, event.text)
                    }
                }
            }
        }
    }

    private fun navigateToLogin() {
        activity?.startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
    }

    override fun onItemClick(stock: Stock) {
        findNavController().navigate(
            StocksListFragmentDirections.actionStocksListFragmentToStockSettingsFragment(
                stock
            )
        )
    }

}