package com.example.flashword.di.components

import androidx.lifecycle.ViewModel
import com.example.flashword.di.ScreenScope
import com.example.flashword.di.ViewModelClassKey
import com.example.flashword.presentation.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Subcomponent()
@ScreenScope
interface LoginComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

}
