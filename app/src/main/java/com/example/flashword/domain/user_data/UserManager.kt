package com.example.flashword.domain.user_data

import com.example.flashword.di.AppScope
import com.example.flashword.di.components.UserComponent
import com.example.flashword.domain.repos.UserPreferencesRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@AppScope
class UserManager @Inject constructor(
    private val userPreferencesRepo: UserPreferencesRepo,
    private val userComponentFactory: UserComponent.Factory,
) {

    var userComponent: UserComponent? = null

    fun getUserName() = flow {
        val result = userPreferencesRepo.getUserName()
        emit(result.getOrNull().orEmpty())
    }

    fun getUserId() = flow {
        val result = userPreferencesRepo.getUserId()
        emit(result.getOrNull().orEmpty())
    }

    fun getUserPictureUrl() = flow {
        val result = userPreferencesRepo.getProfilePictureUrl()
        emit(result.getOrNull().orEmpty())
    }

    suspend fun onLogin(userId: String, userName: String, userPictureUrl: String) = with(userPreferencesRepo) {
        setUserId(userId)
        setUserName(userName)
        setProfilePictureUrl(userPictureUrl)

        userComponent = userComponentFactory.create()
    }

    suspend fun onLogout() = with(userPreferencesRepo) {
        setUserId("")
        setUserName("")
        setProfilePictureUrl("")

        userComponent = null
    }



}