package com.example.flashword.domain.repos

import com.example.flashword.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUser: Flow<UserModel?>
    val currentUserEmail: String
    val currentUserId: String
    fun hasUser(): Boolean
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun signOut()
    suspend fun deleteAccount()

}