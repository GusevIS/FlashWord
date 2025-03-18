package com.example.flashword.domain.repos

import com.example.flashword.domain.model.DeckCreateModel
import com.example.flashword.domain.model.DeckModel
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.Flow

interface DecksRepository {
    suspend fun addDeck(deck: DeckCreateModel)
    fun observeDecks(userId: String, listener: (List<DeckModel>) -> Unit): ListenerRegistration
    fun observeDecks(): Flow<List<DeckModel>>
}