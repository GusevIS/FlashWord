package com.example.flashword.di.components

import androidx.lifecycle.ViewModel
import com.example.flashword.di.ScreenScope
import com.example.flashword.di.ViewModelClassKey
import com.example.flashword.presentation.dashboard.DashboardViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Subcomponent(modules = [])
@ScreenScope
interface DashboardComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): DashboardComponent
    }


}
