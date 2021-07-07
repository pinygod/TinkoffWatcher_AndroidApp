package com.example.tinkoffwatcher.ui.positions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tinkoffwatcher.data.PositionSettings
import com.example.tinkoffwatcher.databinding.FragmentPositionSettingsBinding
import com.example.tinkoffwatcher.viewmodels.PositionSettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PositionSettingsFragment : Fragment() {

    private lateinit var binding: FragmentPositionSettingsBinding
    private lateinit var position: PositionSettings
    private val viewModel by viewModel<PositionSettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = PositionSettingsFragmentArgs.fromBundle(it).position
            viewModel.setPosition(position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPositionSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            position = this@PositionSettingsFragment.position
            viewmodel = viewModel
        }
    }
}