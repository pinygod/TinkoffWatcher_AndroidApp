package com.example.tinkoffwatcher.ui.positions

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
import com.example.tinkoffwatcher.ui.adapters.PositionsListAdapter
import com.example.tinkoffwatcher.data.PositionSettings
import com.example.tinkoffwatcher.databinding.FragmentPositionsListBinding
import com.example.tinkoffwatcher.ui.login.LoginActivity
import com.example.tinkoffwatcher.utils.PositionsEvent
import com.example.tinkoffwatcher.utils.showMessage
import com.example.tinkoffwatcher.viewmodels.PositionsListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class PositionsListFragment : Fragment(), PositionsListAdapter.OnItemClickListener {
    private lateinit var binding: FragmentPositionsListBinding
    private val viewModel by viewModel<PositionsListViewModel>()
    private lateinit var recyclerAdapter: PositionsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPositionsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerAdapter = PositionsListAdapter(this, viewModel)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            positionsRecycler.adapter = recyclerAdapter
            viewmodel = viewModel
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { event ->
                    when (event) {
                        PositionsEvent.NavigateToLogin -> navigateToLogin()
                        is PositionsEvent.Loaded -> recyclerAdapter.submitList(event.positions)
                        is PositionsEvent.ShowMessage -> showMessage(binding.root, event.text)
                    }
                }
            }
        }
    }

    private fun navigateToLogin() {
        activity?.startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
    }

    override fun onItemClick(position: PositionSettings) {
        findNavController().navigate(
            PositionsListFragmentDirections.actionPositionsListFragmentToPositionSettingsFragment(
                position
            )
        )
    }

}