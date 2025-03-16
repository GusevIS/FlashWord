package com.example.flashword.presentation.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashword.R
import com.example.flashword.presentation.components.HeaderText
import com.example.flashword.presentation.components.DefaultOutlineIconTextField
import com.example.flashword.ui.theme.FlashWordTheme

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    state: RegistrationState = RegistrationState(false),
    onUsernameChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onConfirmedPasswordChange: (String) -> Unit = {},

    onSignUpSuccessful: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onSignInClick: () -> Unit = {},
) {
    LaunchedEffect(state.isSignUpSuccessful) {
        if (state.isSignUpSuccessful) {
            onSignUpSuccessful.invoke()
        }
    }

    RegistrationScreenContent(
        state = state,

        onUsernameChange = onUsernameChange,
        onEmailChange = onEmailChange,
        onPasswordChange = onPasswordChange,
        onConfirmedPasswordChange = onConfirmedPasswordChange,

        onSignUpClick = onSignUpClick,
        onSignInClick = onSignInClick
    )
}

@Composable
fun RegistrationScreenContent(
    modifier: Modifier = Modifier,
    state: RegistrationState = RegistrationState(false),

    onUsernameChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onConfirmedPasswordChange: (String) -> Unit = {},

    onSignInClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
) {
    var checked by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(defaultPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(80.dp))

        HeaderText(
            text = stringResource(R.string.sign_up),

            )

        Spacer(Modifier.height(itemSpacing))

        Text(
            text = stringResource(R.string.registration_prompt)
        )

        Spacer(Modifier.height(itemSpacing))

        state.apply {
            DefaultOutlineIconTextField(
                value = username,
                onValueChange = onUsernameChange,
                labelText = stringResource(R.string.username),
                leadingIcon = Icons.Default.Person,
                modifier = Modifier.fillMaxWidth(),
                isError = signUpError == SignUpError.USERNAME_TOO_SHORT,
                errorMessage = when (signUpError) {
                    SignUpError.USERNAME_TOO_SHORT -> stringResource(R.string.sign_up_username_too_short)
                    else -> null
                }
            )
        }

        Spacer(Modifier.height(itemSpacing))

        state.apply {
            DefaultOutlineIconTextField(
                value = email,
                onValueChange = onEmailChange,
                labelText = stringResource(R.string.email),
                leadingIcon = Icons.Default.Email,
                modifier = Modifier.fillMaxWidth(),
                isError = signUpError == SignUpError.INVALID_EMAIL,
                errorMessage = when (signUpError) {
                    SignUpError.INVALID_EMAIL -> stringResource(R.string.sign_up_invalid_email)
                    else -> null
                }
            )
        }

        Spacer(Modifier.height(itemSpacing))

        state.apply {
            DefaultOutlineIconTextField(
                value = password,
                onValueChange = onPasswordChange,
                labelText = stringResource(R.string.password),
                leadingIcon = Icons.Default.Lock,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation(),
                isError = (signUpError == SignUpError.PASSWORD_TOO_SHORT) or (signUpError == SignUpError.INVALID_PASSWORD),
                errorMessage = when (signUpError) {
                    SignUpError.PASSWORD_TOO_SHORT -> stringResource(R.string.sign_up_passwords_too_short)
                    SignUpError.INVALID_PASSWORD -> stringResource(R.string.sign_up_invalid_passwords)
                    else -> null
                }
            )
        }

        Spacer(Modifier.height(itemSpacing))

        state.apply {
            DefaultOutlineIconTextField(
                value = confirmedPassword,
                onValueChange = onConfirmedPasswordChange,
                labelText = stringResource(R.string.confirm_password),
                leadingIcon = Icons.Default.Lock,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation(),
                isError = (signUpError == SignUpError.PASSWORDS_DO_NOT_MATCH),
                errorMessage = when (signUpError) {
                    SignUpError.PASSWORDS_DO_NOT_MATCH -> stringResource(R.string.sign_up_passwords_dont_match)
                    else -> null
                }
            )
        }

        Spacer(Modifier.height(itemSpacing))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it }
            )

            Text(stringResource(R.string.registration_agreement))
        }

        Spacer(Modifier.height(itemSpacing))

        Button(
            onClick = onSignUpClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.sign_up))
        }

        Spacer(Modifier.height(itemSpacing))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.already_have_acc))

            TextButton(onClick = onSignInClick) {
                Text(stringResource(R.string.sign_in))
            }
        }
    }
}

val defaultPadding = 16.dp
val itemSpacing = 8.dp

@Composable
@Preview(showBackground = true)
fun RegistrationScreenContentPreview(

) {
    FlashWordTheme {
        RegistrationScreenContent()
    }
}