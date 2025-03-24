package com.example.flashword.domain.repos

import com.example.flashword.domain.model.CardCreateModel
import com.example.flashword.domain.model.CardModel
import com.example.flashword.domain.model.DeckStatistics
import com.google.firebase.firestore.ListenerRegistration

interface DeckStatisticsRepository {
    suspend fun updateDeckStats(stats: DeckStatistics)
}