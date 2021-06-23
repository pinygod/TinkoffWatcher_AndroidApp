package com.example.tinkoffwatcher.di

import com.example.tinkoffwatcher.viewmodels.LoginViewModel
import com.example.tinkoffwatcher.viewmodels.SplashViewModel
import com.example.tinkoffwatcher.viewmodels.PositionSettingsViewModel
import com.example.tinkoffwatcher.viewmodels.PositionsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { PositionsListViewModel(get(), get()) }
    viewModel { PositionSettingsViewModel(get()) }
}