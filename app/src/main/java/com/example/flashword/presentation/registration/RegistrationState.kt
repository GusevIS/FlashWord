package com.example.flashword.presentation.registration

data class RegistrationState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)