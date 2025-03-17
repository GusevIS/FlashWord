package com.example.flashword

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

open class FlashAppViewModel: ViewModel() {

    fun launchCatching(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                Log.d(ERROR_TAG, throwable.message.orEmpty())
            },
            block = block
        )

    fun <T> asyncCatching(block: suspend CoroutineScope.() -> T): Deferred<T> =
        viewModelScope.async(
            CoroutineExceptionHandler { _, throwable ->
                Log.d(ERROR_TAG, throwable.message.orEmpty())
            }
        ) {
            block()
        }


    companion object {
        const val ERROR_TAG = "VIEW MODEL LAUNCH ERROR"
    }

}