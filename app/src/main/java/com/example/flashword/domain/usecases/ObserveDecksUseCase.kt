package com.example.flashword.domain.usecases

import com.example.flashword.domain.model.DeckModel
import com.example.flashword.domain.repos.DecksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ObserveDecksUseCase @Inject constructor(
    private val decksRepository: DecksRepository
) {
    suspend operator fun invoke(): Flow<List<DeckModel>> {
        return withContext(Dispatchers.IO) {
            decksRepository.observeDecks()
        }
    }
}