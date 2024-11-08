package com.example.flashword.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashword.domain.repos.UserPreferencesRepo
import com.example.flashword.domain.user_data.UserManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val userManager: UserManager,
): ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage

        ) }
    }

    fun resetState() {
        _state.update { SignInState() }
    }

    fun cashUserData(data: UserData) {
        viewModelScope.launch {
            userManager.onLogin(data.userId, data.username.orEmpty(), data.profilePictureUrl.orEmpty())
        }
    }

    fun getCashedUserName() = flow {

        emit(userManager.getUserName().first())


    }



}