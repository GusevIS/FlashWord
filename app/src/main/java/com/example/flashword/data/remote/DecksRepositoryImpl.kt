package com.example.flashword.data.remote

import com.example.flashword.data.source.FirestoreDataSource
import com.example.flashword.domain.model.DeckModel
import com.example.flashword.domain.repos.DecksRepository
import javax.inject.Inject

class DecksRepositoryImpl @Inject constructor(
    private val firestore: FirestoreDataSource,
): DecksRepository {
    override suspend fun addDeck(deck: DeckModel) {
        firestore.addDeck(deck)
    }

    override fun observeDecks(userId: String, listener: (List<DeckModel>) -> Unit) =
        firestore.observeDecks(userId, listener)


}