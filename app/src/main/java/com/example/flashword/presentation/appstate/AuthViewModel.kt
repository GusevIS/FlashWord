package com.example.flashword.presentation.appstate

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.flashword.FlashAppViewModel
import com.example.flashword.domain.model.UserModel
import com.example.flashword.domain.repos.AccountService
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val accountService: AccountService
): FlashAppViewModel() {
    val currentUser: StateFlow<UserModelUI?> = accountService.currentUser
        .map { it?.mapToUI() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = null
        )

}

data class UserModelUI(
    val userName: String? = "",
    val email: String? = "",
    val pictureUrl: String? = "",
    val id: String? = "",
)

fun UserModel.mapToUI() =
    UserModelUI(
        userName = this.name,
        email = this.email,
        id = this.id,
        pictureUrl = this.pictureUrl
    )