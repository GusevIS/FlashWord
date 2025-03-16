package com.example.flashword

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.flashword.presentation.appstate.AppState
import com.example.flashword.presentation.appstate.rememberAppState
import com.example.flashword.presentation.login.LoginScreen
import com.example.flashword.presentation.navigation.AppNavHost
import com.example.flashword.presentation.navigation.clearAndNavigate
import com.example.flashword.ui.theme.FlashWordTheme
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as FlashWordApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        val fs = Firebase.firestore
        fs.collection("decks")
            .document().set(mapOf("user" to "hui"))

        //enableEdgeToEdge()

        setContent {
            FlashWordContent(
                viewModelFactory
            )
        }
    }
}

@Composable
fun FlashWordContent(
    viewModelFactory: ViewModelProvider.Factory
) {
    FlashWordTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val getViewModelFactory: () -> ViewModelProvider.Factory = remember {
                { viewModelFactory }
            }

            val appState = rememberAppState(viewModel(factory = getViewModelFactory()))

            AppNavHost(
                navController = appState.navHostController,
                getViewModelFactory = getViewModelFactory,
                appState = appState
            )

        }
    }
}