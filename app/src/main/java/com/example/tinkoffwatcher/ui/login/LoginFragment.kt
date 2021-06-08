package com.example.tinkoffwatcher.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tinkoffwatcher.ui.stocks.StocksActivity
import com.example.tinkoffwatcher.databinding.FragmentLoginBinding
import com.example.tinkoffwatcher.utils.LoginEvent
import com.example.tinkoffwatcher.utils.showMessage
import com.example.tinkoffwatcher.viewmodels.LoginViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val viewModel by viewModel<LoginViewModel>()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { event ->
                    when (event) {
                        LoginEvent.NavigateToMainScreen -> NavigateToMainScreen()
                        is LoginEvent.ShowMessage -> showMessage(binding.root, event.text)
                    }
                }
            }
        }

    }

    private fun NavigateToMainScreen() {
        activity?.startActivity(Intent(activity, StocksActivity::class.java))
        activity?.finish()
    }

}