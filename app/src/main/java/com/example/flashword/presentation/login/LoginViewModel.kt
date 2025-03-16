package com.example.flashword.presentation.login

import android.util.Log
import com.example.flashword.FlashAppViewModel
import com.example.flashword.domain.repos.AccountService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val accountService: AccountService
): FlashAppViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun resetState() {
        _state.update { SignInState() }
    }

    fun updateEmail(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun onSignInClick() {
        launchCatching {
            accountService.signIn(state.value.email.trim(), state.value.password.trim())
            _state.value = _state.value.copy( isSignInSuccessful = true)
        }

    }

}