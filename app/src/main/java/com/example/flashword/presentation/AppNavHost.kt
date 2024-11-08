package com.example.flashword.presentation

import android.app.Activity.RESULT_OK
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flashword.presentation.dashboard.DashboardScreen
import com.example.flashword.presentation.login.GoogleAuthUiClient
import com.example.flashword.presentation.login.LoginScreen
import com.example.flashword.presentation.login.LoginViewModel
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

@Composable
fun AppNavHost(
    ctx: Context,
    googleAuthUiClient: GoogleAuthUiClient,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModelFactory: ViewModelProvider.Factory,
    startDestination: String = NavigationItem.Login.route,
) {
    val getViewModelFactory: () -> ViewModelProvider.Factory = remember {
        { viewModelFactory }
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination) {


        composable(NavigationItem.Login.route) {
            Log.d("qwrzsr", "qwr-------------")
            val viewModel: LoginViewModel = viewModel(factory = getViewModelFactory())
            val state by viewModel.state.collectAsStateWithLifecycle()

            val scope = rememberCoroutineScope()
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if(result.resultCode == RESULT_OK) {
                        scope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            val onSignInSuccessful = {
                googleAuthUiClient.getSignedIdUser()?.let { viewModel.cashUserData(it) }
                navController.navigate(NavigationItem.Dashboard.route)
                viewModel.resetState()
            }

//            LaunchedEffect(key1 = state.isSignInSuccessful) {
//                if(state.isSignInSuccessful) {
//                    Toast.makeText(
//                        ctx,
//                        "Sign in successful",
//                        Toast.LENGTH_LONG
//                    ).show()
//
//                    navController.navigate(NavigationItem.Dashboard.route)
//                    viewModel.resetState()
//                }
//            }

            LoginScreen(
                navController = navController,
                viewModel = viewModel,
                onSignInSuccessful = onSignInSuccessful
            ) {
                scope.launch {
                    val signInIntentSender = googleAuthUiClient.signIn()
                    launcher.launch(
                        IntentSenderRequest.Builder(
                            signInIntentSender ?: return@launch
                        ).build()
                    )
                }
            }
        }

        composable(NavigationItem.Dashboard.route) {
            DashboardScreen(
                navController = navController,
                userData = googleAuthUiClient.getSignedIdUser(),
                getViewModelFactory = getViewModelFactory
            )
        }

    }
}