package com.example.tinkoffwatcher.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tinkoffwatcher.ui.login.LoginActivity
import com.example.tinkoffwatcher.ui.stocks.StocksActivity
import com.example.tinkoffwatcher.viewmodels.SplashViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.loginState.collect { event ->
                    when(event){
                        SplashViewModel.Event.NavigateToLogin -> NavigateToLogin()
                        SplashViewModel.Event.NavigateToMainScreen -> NavigateToMainScreen()
                    }
                }
            }
        }
    }

    private fun NavigateToMainScreen() {
        startActivity(Intent(this, StocksActivity::class.java))
        finish()
    }

    private fun NavigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}