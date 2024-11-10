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
    state: RegistrationState = RegistrationState(false, null),
    onSignUpSuccessful: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onSignInClick: () -> Unit = {},
) {
    RegistrationScreenContent(
        state = state,
        onSignUpSuccessful = onSignUpSuccessful,
        onSignUpClick = onSignUpClick,
        onSignInClick = onSignInClick
    )
}

@Composable
fun RegistrationScreenContent(
    modifier: Modifier = Modifier,
    state: RegistrationState = RegistrationState(false, null),
    onSignUpSuccessful: () -> Unit = {},
    onSignInClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
) {
    val (userName, setUsername) = rememberSaveable {
        mutableStateOf("")
    }

    val (userEmail, setUserEmail) = rememberSaveable {
        mutableStateOf("")
    }

    val (userPassword, setUserPassword) = rememberSaveable {
        mutableStateOf("")
    }

    val (userConfirmedPassword, setUserConfirmedPassword) = rememberSaveable {
        mutableStateOf("")
    }

    var checked by rememberSaveable { mutableStateOf(true) }

    val isFieldsEmpty = userName.isNotEmpty() && userPassword.isNotEmpty()

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

        DefaultOutlineIconTextField(
            value = userName,
            onValueChange = setUsername,
            labelText = stringResource(R.string.username),
            leadingIcon = Icons.Default.Person,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(itemSpacing))

        DefaultOutlineIconTextField(
            value = userEmail,
            onValueChange = setUserEmail,
            labelText = stringResource(R.string.email),
            leadingIcon = Icons.Default.Email,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(itemSpacing))

        DefaultOutlineIconTextField(
            value = userPassword,
            onValueChange = setUserPassword,
            labelText = stringResource(R.string.password),
            leadingIcon = Icons.Default.Lock,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(itemSpacing))

        DefaultOutlineIconTextField(
            value = userConfirmedPassword,
            onValueChange = setUserConfirmedPassword,
            labelText = stringResource(R.string.confirm_password),
            leadingIcon = Icons.Default.Lock,
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )

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