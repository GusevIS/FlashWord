package com.example.flashword.domain.repos

import com.example.flashword.domain.model.DeckModel
import com.google.firebase.firestore.ListenerRegistration

interface DecksRepository {
    suspend fun addDeck(deck: DeckModel)
    fun observeDecks(userId: String, listener: (List<DeckModel>) -> Unit): ListenerRegistration
}