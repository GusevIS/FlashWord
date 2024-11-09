package com.example.flashword.di.components

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flashword.MainActivity
import com.example.flashword.data.local.UserPreferencesRepoImpl
import com.example.flashword.di.AppContext
import com.example.flashword.di.AppScope
import com.example.flashword.di.MultiViewModelFactory
import com.example.flashword.di.ViewModelClassKey
import com.example.flashword.di.modules.UserPreferencesModule
import com.example.flashword.domain.repos.UserPreferencesRepo
import com.example.flashword.domain.user_data.UserManager
import com.example.flashword.presentation.dashboard.DashboardViewModel
import com.example.flashword.presentation.login.LoginViewModel
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Component(
    modules = [AppSubcomponents::class, ViewModelBindsModule::class, AuthModule::class, AppModule::class,
    UserPreferencesModule::class]
)
@AppScope
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun userManager(): UserManager

//    fun loginComponent(): LoginComponent.Factory
//
    fun inject(activity: MainActivity)

    val viewModelFactory: MultiViewModelFactory

}

@Module
class AppModule {
    @Provides
    @AppScope
    @AppContext
    fun provideAppContext(
        context: Context
    ): Context {
        return context.applicationContext
    }
}

@Module
interface ViewModelBindsModule {
    @Binds
    fun bindsViewModelFactory(factory: MultiViewModelFactory): ViewModelProvider.Factory

    @Binds
    @[IntoMap ViewModelClassKey(LoginViewModel::class)]
    fun bindsLoginViewModule(viewModel: LoginViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelClassKey(DashboardViewModel::class)]
    fun bindsDashboardViewModule(viewModel: DashboardViewModel): ViewModel

}

@Module
class AuthModule {

}

@Module(subcomponents = [LoginComponent::class, DashboardComponent::class, UserComponent::class])
class AppSubcomponents
