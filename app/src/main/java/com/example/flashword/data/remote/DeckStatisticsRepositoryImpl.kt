package com.example.flashword.data.remote

import com.example.flashword.data.model.toDeckStatisticsDto
import com.example.flashword.data.source.FirestoreDataSource
import com.example.flashword.domain.model.DeckStatistics
import com.example.flashword.domain.repos.DeckStatisticsRepository
import javax.inject.Inject

class DeckStatisticsRepositoryImpl @Inject constructor(
    private val firestore: FirestoreDataSource
): DeckStatisticsRepository {
    override suspend fun updateDeckStats(stats: DeckStatistics) {
        firestore.updateDeckStats(stats.toDeckStatisticsDto())
    }
}