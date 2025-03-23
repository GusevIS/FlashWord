package com.example.flashword.domain.usecases

import android.util.Log
import com.example.flashword.domain.model.CardModel
import com.example.flashword.domain.repos.CardsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCardsForReviewUseCase @Inject constructor(
    private val cardsRepository: CardsRepository
){

    suspend operator fun invoke(deckId: String, nextReviewAt: Long = System.currentTimeMillis()): List<CardModel> {
        return withContext(Dispatchers.IO) {
            cardsRepository.getCardsForReviewFromDeck(deckId, nextReviewAt)
//            s.forEach { Log.d("qweds ", it.toString()) }
//            s
        }
    }
}