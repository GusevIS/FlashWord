package com.example.flashword

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.flashword.presentation.AppNavHost
import com.example.flashword.ui.theme.FlashWordTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as FlashWordApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)

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
            AppNavHost(
                navController = rememberNavController(),
                viewModelFactory = viewModelFactory
            )

        }
    }


}