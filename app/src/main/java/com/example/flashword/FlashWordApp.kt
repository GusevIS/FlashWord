package com.example.flashword

import android.app.Application
import android.util.Log
import com.example.flashword.di.components.AppComponent
import com.example.flashword.di.components.DaggerAppComponent

open class FlashWordApp: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

}