package com.theobencode.victoroben.sampleproject

import android.app.Application
import com.theobencode.victoroben.sampleproject.di.networkModule
import com.theobencode.victoroben.sampleproject.di.repositoryModule
import com.theobencode.victoroben.sampleproject.di.viewModelModule
import org.koin.android.ext.android.startKoin

class SampleApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(networkModule, viewModelModule, repositoryModule))
    }
}