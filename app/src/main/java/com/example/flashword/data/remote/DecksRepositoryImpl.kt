package com.example.flashword.data.remote

import com.example.flashword.data.model.DeckDto
import com.example.flashword.data.model.toDeckCreateDto
import com.example.flashword.data.model.toDeckDto
import com.example.flashword.data.model.toDeckModel
import com.example.flashword.data.source.FirestoreDataSource
import com.example.flashword.domain.model.DeckCreateModel
import com.example.flashword.domain.model.DeckModel
import com.example.flashword.domain.repos.DecksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DecksRepositoryImpl @Inject constructor(
    private val firestore: FirestoreDataSource,
): DecksRepository {
    override suspend fun addDeck(deck: DeckCreateModel) {
        firestore.addDeck(deck.toDeckCreateDto())
    }

    override fun observeDecks(userId: String, listener: (List<DeckModel>) -> Unit) =
        firestore.observeDecks(userId) { deckModelList ->
            val deckDtoList = deckModelList.map { it.toDeckModel() }
            listener(deckDtoList)
        }

    override fun observeDecks(): Flow<List<DeckModel>> =
        firestore.observeDecks().map { deckDtoList ->
            deckDtoList .map { it.toDeckModel() }
        }


}