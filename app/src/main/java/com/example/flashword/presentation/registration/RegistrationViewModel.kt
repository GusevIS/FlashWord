package com.example.flashword.presentation.registration

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashword.FlashAppViewModel
import com.example.flashword.R
import com.example.flashword.domain.repos.AccountService
import com.example.flashword.domain.repos.UserPreferencesRepo
import com.example.flashword.domain.user_data.UserManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val accountService: AccountService
): FlashAppViewModel() {
    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    fun resetState() {
        _state.update { RegistrationState() }
    }

    fun updateUsername(username: String) {
        _state.value = _state.value.copy(username = username)
    }

    fun updateEmail(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun updateConfirmedPassword(confirmedPassword: String) {
        _state.value = _state.value.copy(confirmedPassword = confirmedPassword)
    }

    fun onSignUpClick() {
        validateUserData()
        if (_state.value.signUpError == SignUpError.NONE)
            launchCatching {
                accountService.signUp(state.value.email.trim(), state.value.password.trim())
                _state.value = _state.value.copy( isSignUpSuccessful = true)
            }
    }

    private fun validateUserData() = _state.value.apply {
        // Regexp:
        // - at least one digit (?=.*[0-9])
        // - at least one lowercase letter (?=.*[a-z])
        // - at least one uppercase letter (?=.*[A-Z])
        // - at least 8 symbols (.{8,})
        val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*\$".toRegex()

        val signUpError: SignUpError = if (password != confirmedPassword) SignUpError.PASSWORDS_DO_NOT_MATCH
        else if (password.length < MIN_PASSWORD_LENGTH) SignUpError.PASSWORD_TOO_SHORT
        else if (!password.matches(passwordRegex)) SignUpError.INVALID_PASSWORD
        else if (username.length < MIN_USERNAME_LENGTH) SignUpError.USERNAME_TOO_SHORT
        else if ((email.isBlank()) or (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())) SignUpError.INVALID_EMAIL
        else SignUpError.NONE

        _state.update { it.copy(signUpError = signUpError) }
        Log.d("test43", "${_state.value.signUpError} $password")
    }


    companion object {
        const val MIN_PASSWORD_LENGTH = 8
        const val MIN_USERNAME_LENGTH = 4
    }

}