package com.example.flashword.data.remote

import com.example.flashword.domain.model.CardModel
import com.example.flashword.domain.repos.CardsRepository
import com.google.firebase.firestore.ListenerRegistration
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(

): CardsRepository {
    override suspend fun addCard(deckId: String, card: CardModel) {
        TODO("Not yet implemented")
    }

    override fun observeCards(
        deckId: String,
        listener: (List<CardModel>) -> Unit
    ): ListenerRegistration {
        TODO("Not yet implemented")
    }
}