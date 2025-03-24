package com.example.flashword.di.components

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flashword.MainActivity
import com.example.flashword.data.remote.AccountServiceImpl
import com.example.flashword.data.remote.CardsRepositoryImpl
import com.example.flashword.data.remote.DeckStatisticsRepositoryImpl
import com.example.flashword.data.remote.DecksRepositoryImpl
import com.example.flashword.di.AppContext
import com.example.flashword.di.AppScope
import com.example.flashword.di.MultiViewModelFactory
import com.example.flashword.di.ViewModelClassKey
import com.example.flashword.di.modules.UserPreferencesModule
import com.example.flashword.domain.repos.AccountService
import com.example.flashword.domain.repos.CardsRepository
import com.example.flashword.domain.repos.DeckStatisticsRepository
import com.example.flashword.domain.repos.DecksRepository
import com.example.flashword.domain.user_data.UserManager
import com.example.flashword.presentation.addcard.AddCardViewModel
import com.example.flashword.presentation.appstate.AuthViewModel
import com.example.flashword.presentation.dashboard.DashboardViewModel
import com.example.flashword.presentation.login.LoginViewModel
import com.example.flashword.presentation.profile.ProfileViewModel
import com.example.flashword.presentation.registration.RegistrationViewModel
import com.example.flashword.presentation.splash.SplashViewModel
import com.example.flashword.presentation.studying_cards.StudyingCardsViewModel
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Component(
    modules = [AppSubcomponents::class, ViewModelBindsModule::class, AuthModule::class, AppModule::class,
    UserPreferencesModule::class, AppsBinds::class]
)
@AppScope
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)

    val viewModelFactory: MultiViewModelFactory

    fun addCardViewModelFactory(): AddCardViewModel.AddCardViewModelFactory.Factory
    fun studyingCardsViewModelFactory(): StudyingCardsViewModel.StudyingCardsViewModelFactory.Factory


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
    @[IntoMap ViewModelClassKey(AuthViewModel::class)]
    fun bindsAuthViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelClassKey(SplashViewModel::class)]
    fun bindsSplashViewModule(viewModel: SplashViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelClassKey(LoginViewModel::class)]
    fun bindsLoginViewModule(viewModel: LoginViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelClassKey(RegistrationViewModel::class)]
    fun bindsRegistrationViewModule(viewModel: RegistrationViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelClassKey(DashboardViewModel::class)]
    fun bindsDashboardViewModule(viewModel: DashboardViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelClassKey(ProfileViewModel::class)]
    fun bindsProfileViewModel(viewModel: ProfileViewModel): ViewModel

//    @Binds
//    @[IntoMap ViewModelClassKey(StatisticsViewModel::class)]
//    fun bindsStatisticsViewModel(viewModel: StatisticsViewModel): ViewModel
}

@Module
abstract class AppsBinds {
    @Binds
    abstract fun bindsAccountService(service: AccountServiceImpl): AccountService

    @Binds
    abstract fun bindsDecksRepository(repo: DecksRepositoryImpl): DecksRepository

    @Binds
    abstract fun bindsCardsRepository(repo: CardsRepositoryImpl): CardsRepository

    @Binds
    abstract fun bindsDeckStatisticsRepository(repo: DeckStatisticsRepositoryImpl): DeckStatisticsRepository
}

@Module
class AuthModule {

}

@Module(subcomponents = [LoginComponent::class, DashboardComponent::class, UserComponent::class])
class AppSubcomponents
