package com.example.flashword.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flashword.data.local.UserPreferencesRepoImpl
import com.example.flashword.di.AppContext
import com.example.flashword.di.AppScope
import com.example.flashword.domain.repos.UserPreferencesRepo
import dagger.Binds
import dagger.Module
import dagger.Provides

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_preferences")

@Module
interface UserPreferencesModule {

    @Binds
    @AppScope
    fun bindUserPreferencesRepo(
        userPreferencesRepoImpl: UserPreferencesRepoImpl
    ): UserPreferencesRepo

    companion object {

        @Provides
        @AppScope
        fun provideUserDataStorePreferences(
            @AppContext context: Context
        ): DataStore<Preferences> {
            return context.dataStore
        }
    }

}
