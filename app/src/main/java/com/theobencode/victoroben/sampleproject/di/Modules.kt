package com.theobencode.victoroben.sampleproject.di

import com.theobencode.victoroben.sampleproject.GithubRepoRepository
import com.theobencode.victoroben.sampleproject.GithubViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { GithubViewModel(get()) }
}

val repositoryModule = module {
    single { GithubRepoRepository(get()) }
}

