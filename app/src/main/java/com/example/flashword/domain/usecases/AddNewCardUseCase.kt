package com.example.flashword.domain.usecases

import com.example.flashword.domain.model.CardCreateModel
import com.example.flashword.domain.model.CardModel
import com.example.flashword.domain.model.CardStatus
import com.example.flashword.domain.repos.CardsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddNewCardUseCase @Inject constructor(
    private val addCardsRepository: CardsRepository
) {
    suspend fun execute(deckId: String, frontText: String, backText: String) {
        val createdAt = System.currentTimeMillis()
        val card = CardCreateModel(
            deckId = deckId,
            frontText = frontText,
            backText = backText,
            createdAt = createdAt,
            lastReviewAt = createdAt,
            nextReviewAt = createdAt,
            wasForgotten = false
        )
        return withContext(Dispatchers.IO) {
            addCardsRepository.addCard(card)
        }
    }
}