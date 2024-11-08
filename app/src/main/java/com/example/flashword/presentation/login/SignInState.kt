package com.example.flashword.presentation.login

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)