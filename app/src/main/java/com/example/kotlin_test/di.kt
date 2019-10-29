package com.example.kotlin_test


import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module

val firstModule = module {

    // ViewModel
    viewModel<ViewModel>()
}

