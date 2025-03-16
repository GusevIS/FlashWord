package com.example.flashword.data.remote

import com.example.flashword.domain.model.UserModel
import com.example.flashword.domain.repos.AccountService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(

) : AccountService {
    override val currentUser: Flow<UserModel?>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                this.trySend(auth.currentUser?.let { UserModel(
                    id = it.uid,
                    name = it.displayName,
                    email = it.email,
                    pictureUrl = it.photoUrl.toString()) })
            }

            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener)}

        }

    override val currentUserEmail: String
        get() = Firebase.auth.currentUser?.email.orEmpty()

    override val currentUserId: String
        get() = Firebase.auth.currentUser?.uid.orEmpty()

    override fun hasUser(): Boolean = Firebase.auth.currentUser != null

    override suspend fun signIn(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signUp(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun signOut() {
        Firebase.auth.signOut()
    }

    override suspend fun deleteAccount() {
        TODO("Not yet implemented")
    }
}