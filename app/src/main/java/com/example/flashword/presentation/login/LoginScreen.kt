package com.example.flashword.presentation.login

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.flashword.R
import com.example.flashword.presentation.components.HeaderText
import com.example.flashword.presentation.login.components.LoginTextField
import com.example.flashword.ui.theme.FlashWordTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
//    getViewModelFactory: () -> ViewModelProvider.Factory,
//    viewModel: LoginViewModel = viewModel(factory = getViewModelFactory()),
    viewModel: LoginViewModel,
    onSignInSuccessful: () -> Unit,
    onSignInClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onGoogleSignInClick: () -> Unit

) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()





    LoginScreenContent(
        uiState = uiState,
        onSignInSuccessful = onSignInSuccessful,
        onSignInClick = onSignInClick,
        onSignUpClick = onSignUpClick,
        onGoogleSignInClick = onGoogleSignInClick
    )


}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignInState,
    forgotPassword: () -> Unit = {},
    onSignInSuccessful: () -> Unit = {},
    onSignInClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onGoogleSignInClick: () -> Unit = {}
) {
    val (userName, setUsername) = rememberSaveable {
        mutableStateOf("")
    }

    val (userPassword, setUserPassword) = rememberSaveable {
        mutableStateOf("")
    }

    val isFieldsEmpty = userName.isNotEmpty() && userPassword.isNotEmpty()

    val context = LocalContext.current
    LaunchedEffect(key1 = uiState.signInError) {
        uiState.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    LaunchedEffect(key1 = uiState.isSignInSuccessful) {
        if(uiState.isSignInSuccessful) {
            Toast.makeText(
                context,
                "Sign in successful",
                Toast.LENGTH_LONG
            ).show()

            onSignInSuccessful()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(defaultPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(Modifier.height(64.dp))

        HeaderText(
            text = stringResource(R.string.sign_in),

            )

        Spacer(Modifier.height(itemSpacing))

        Text(
            text = stringResource(R.string.login_prompt)
        )

        Spacer(Modifier.height(itemSpacing))

        LoginTextField(
            value = userName,
            onValueChange = setUsername,
            labelText = stringResource(R.string.username),
            leadingIcon = Icons.Default.Person,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(itemSpacing))

        LoginTextField(
            value = userPassword,
            onValueChange = setUserPassword,
            labelText = stringResource(R.string.password),
            leadingIcon = Icons.Default.Lock,
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(itemSpacing))

        TextButton(
            onClick = forgotPassword,
            modifier = Modifier.align(alignment = Alignment.End),
        ) {
            Text(stringResource(R.string.forgot_password))
        }

        Spacer(Modifier.height(itemSpacing))

        Button(
            onClick = onSignInClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Sign in")
        }

        Spacer(Modifier.height(itemSpacing))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {


            Text(stringResource(R.string.dont_have_acc))

            TextButton(onClick = onSignUpClick) {
                Text(stringResource(R.string.sign_up))
            }
        }



        Column(
            Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.or_sign_in_with))

            Spacer(Modifier.height(itemSpacing))

            Icon(
                painter = painterResource(R.drawable.ic_google),
                contentDescription = "alternative Login",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        onGoogleSignInClick()
                    }
                    .clip(CircleShape)
            )
        }



//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            contentAlignment = Alignment.Center
//        ) {
//
//        }
    }


}

val defaultPadding = 16.dp
val itemSpacing = 8.dp

@Composable
@Preview(showBackground = true)
fun LoginScreenContentPreview(

) {
    FlashWordTheme {
        LoginScreenContent(
            uiState = SignInState(false, null),
        )
    }

}