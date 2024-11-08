package com.example.flashword.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import javax.inject.Qualifier
import javax.inject.Scope
import kotlin.reflect.KClass

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope {
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ScreenScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelScope


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class LoginFlowScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class LoggedUserScope

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelClassKey(val value: KClass<out ViewModel>)


@Qualifier
annotation class AppContext