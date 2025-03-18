package com.example.flashword.domain.usecases

import com.example.flashword.domain.model.DeckCreateModel
import com.example.flashword.domain.repos.DecksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddNewDeckUseCase @Inject constructor(
    private val decksRepository: DecksRepository
) {
    suspend operator fun invoke(userId: String, title: String) {
        val deck = DeckCreateModel(
            userId = userId,
            title = title
        )

        return withContext(Dispatchers.IO) {
            decksRepository.addDeck(deck)
        }
    }
}