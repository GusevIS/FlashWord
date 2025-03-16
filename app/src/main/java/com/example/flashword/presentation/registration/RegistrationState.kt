package com.example.flashword.presentation.registration

data class RegistrationState(
    val isSignUpSuccessful: Boolean = false,
    val signUpError: SignUpError = SignUpError.NONE,
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmedPassword: String = "",
)

enum class SignUpError {
    PASSWORDS_DO_NOT_MATCH,
    PASSWORD_TOO_SHORT,
    USERNAME_TOO_SHORT,
    INVALID_EMAIL,
    INVALID_PASSWORD,

    USER_ALREADY_EXISTS,

    NONE,
}