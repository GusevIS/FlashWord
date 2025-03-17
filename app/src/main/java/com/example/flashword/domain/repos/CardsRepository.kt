package com.example.flashword.domain.repos

import com.example.flashword.domain.model.CardModel
import com.example.flashword.domain.model.DeckModel
import com.google.firebase.firestore.ListenerRegistration

interface CardsRepository {
    suspend fun addCard(deckId: String, card: CardModel)
    fun observeCards(deckId: String, listener: (List<CardModel>) -> Unit): ListenerRegistration
}