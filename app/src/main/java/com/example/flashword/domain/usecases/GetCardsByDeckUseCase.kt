package com.example.flashword.domain.usecases

import com.example.flashword.domain.model.CardModel
import com.example.flashword.domain.repos.CardsRepository
import com.example.flashword.domain.repos.DecksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCardsByDeckUseCase @Inject constructor(
    private val cardsRepository: CardsRepository
){

    suspend operator fun invoke(deckId: String): List<CardModel> {
        return withContext(Dispatchers.IO) {
            cardsRepository.getCardsFromDeck(deckId)
        }
    }
}