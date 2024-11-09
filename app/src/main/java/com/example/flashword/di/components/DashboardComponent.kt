package com.example.flashword.di.components

import com.example.flashword.di.ScreenScope
import dagger.Subcomponent

@Subcomponent(modules = [])
@ScreenScope
interface DashboardComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): DashboardComponent
    }


}
