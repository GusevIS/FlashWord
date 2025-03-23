package com.example.flashword.data.remote

import com.example.flashword.data.model.toCardCreateDto
import com.example.flashword.data.model.toCardDto
import com.example.flashword.data.model.toCardModel
import com.example.flashword.data.model.toTimestamp
import com.example.flashword.data.source.FirestoreDataSource
import com.example.flashword.domain.model.CardCreateModel
import com.example.flashword.domain.model.CardModel
import com.example.flashword.domain.repos.CardsRepository
import com.google.firebase.firestore.ListenerRegistration
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(
    private val firestore: FirestoreDataSource
): CardsRepository {
    override suspend fun addCard(card: CardCreateModel) {
        firestore.addCard(card.toCardCreateDto())
    }

    override suspend fun getCardsFromDeck(deckId: String): List<CardModel> {
        return firestore.getCardsFromDeck(deckId).map { it.toCardModel() }
    }

    override suspend fun getCardsForReviewFromDeck(deckId: String, nextReviewAt: Long): List<CardModel> {
        return firestore.getCardsForReviewFromDeck(deckId, nextReviewAt.toTimestamp()).map { it.toCardModel() }
    }

    override fun updateCard(card: CardModel) {
        firestore.updateCard(card.toCardDto())
    }

    override fun observeCards(
        deckId: String,
        listener: (List<CardModel>) -> Unit
    ): ListenerRegistration {
        TODO("Not yet implemented")
    }
}