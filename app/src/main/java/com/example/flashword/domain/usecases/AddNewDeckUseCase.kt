package com.example.flashword.domain.usecases

import com.example.flashword.domain.model.DeckCreateModel
import com.example.flashword.domain.repos.AccountService
import com.example.flashword.domain.repos.DecksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddNewDeckUseCase @Inject constructor(
    private val accountService: AccountService,
    private val decksRepository: DecksRepository
) {
    suspend operator fun invoke(title: String) {
        val deck = DeckCreateModel(
            userId = accountService.currentUserId,
            title = title
        )

        return withContext(Dispatchers.IO) {
            decksRepository.addDeck(deck)
        }
    }
}