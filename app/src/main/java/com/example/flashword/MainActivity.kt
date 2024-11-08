package com.example.flashword

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.navigation.compose.rememberNavController
import com.example.flashword.presentation.AppNavHost
import com.example.flashword.presentation.dashboard.DashboardViewModel
import com.example.flashword.presentation.login.GoogleAuthUiClient
import com.example.flashword.presentation.login.LoginViewModel
import com.example.flashword.ui.theme.FlashWordTheme
import com.google.android.gms.auth.api.identity.Identity
import dagger.Lazy
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var googleAuthUiClient: Lazy<GoogleAuthUiClient>

//    private val googleAuthUiClient by lazy {
//        GoogleAuthUiClient(
//            context = applicationContext,
//            oneTapClient = Identity.getSignInClient(applicationContext)
//        )
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as FlashWordApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            FlashWordContent(
                application as FlashWordApp,
                googleAuthUiClient.get(),
                viewModelFactory
            )
        }
    }

}

@Composable
fun FlashWordContent(
    app: FlashWordApp,
    googleAuthUiClient: GoogleAuthUiClient,
    viewModelFactory: ViewModelProvider.Factory
) {
    FlashWordTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppNavHost(
                app,
                googleAuthUiClient,
                navController = rememberNavController(),
                viewModelFactory = viewModelFactory
            )

        }
    }


}