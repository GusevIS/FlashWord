package com.example.flashword.presentation.dashboard

import com.example.flashword.presentation.dashboard.deck.DeckState

data class DashboardUiState(
    val userName: String = "testuser",
    var cardDecks: List<DeckState> = emptyList(),
    val totalCardsToReview: Int = 37,

    )