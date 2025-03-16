package com.example.flashword.presentation.dashboard

import com.example.flashword.presentation.dashboard.deck.DeckState

data class DashboardUiState(
    val userName: String = "testuser",
    val cardDecks: List<DeckState> = listOf(
        DeckState("English verbs", 81, 12, 8, 35, 0.7f, 65),
        DeckState("French verbs", 81, 0, 0 , 0, 1.0f),
        DeckState("Vasau nima", 81, 12, 8, 35, 0.2f, 35),
        DeckState("English verbs", 81, 12, 8, 35, 0.7f, 65),
        DeckState("English verbs", 81, 12, 8, 35, 0.7f, 65),
        DeckState("English verbs", 81, 12, 8, 35, 0.7f, 65),
    ),
    val totalCardsToReview: Int = 37,


)