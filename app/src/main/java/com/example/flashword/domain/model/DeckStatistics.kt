package com.example.flashword.domain.model

data class DeckStatistics(
    var deckId: String,
    val date: Long,
    val cardsDue: Int,
    val reviewedCount: Int,
    )