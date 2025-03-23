package com.example.flashword.domain.repos

import com.example.flashword.domain.model.CardCreateModel
import com.example.flashword.domain.model.CardModel
import com.example.flashword.domain.model.DeckModel
import com.google.firebase.firestore.ListenerRegistration

interface CardsRepository {
    suspend fun addCard(card: CardCreateModel)
    suspend fun getCardsFromDeck(deckId: String): List<CardModel>
    suspend fun getCardsForReviewFromDeck(deckId: String, nextReviewAt: Long): List<CardModel>
    fun observeCards(deckId: String, listener: (List<CardModel>) -> Unit): ListenerRegistration
    fun updateCard(card: CardModel)
}