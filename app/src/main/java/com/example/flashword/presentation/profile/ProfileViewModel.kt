package com.example.flashword.presentation.profile

import com.example.flashword.FlashAppViewModel
import com.example.flashword.domain.repos.AccountService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    val accountService: AccountService
): FlashAppViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()



    fun signOut() {
        launchCatching {
            accountService.signOut()
        }
    }
}