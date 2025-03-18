package com.example.flashword.data.remote

import com.example.flashword.data.model.toCardDto
import com.example.flashword.data.model.toCardModel
import com.example.flashword.data.source.FirestoreDataSource
import com.example.flashword.domain.model.CardModel
import com.example.flashword.domain.repos.CardsRepository
import com.google.firebase.firestore.ListenerRegistration
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(
    private val firestore: FirestoreDataSource
): CardsRepository {
    override suspend fun addCard(card: CardModel) {
        firestore.addCard(card.toCardDto())
    }

    override suspend fun getCardsFromDeck(deckId: String): List<CardModel> {
        return firestore.getCardsFromDeck(deckId).map { it.toCardModel() }
    }

    override fun observeCards(
        deckId: String,
        listener: (List<CardModel>) -> Unit
    ): ListenerRegistration {
        TODO("Not yet implemented")
    }
}