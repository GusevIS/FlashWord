package com.example.flashword.domain.model

data class CardModel(
    val deckId: String,
    val frontText: String,
    val backText: String,

    val createdAt: Long,
    val lastReviewAt: Long,
    val nextReviewAt: Long,

    val status: CardStatus

)

enum class CardStatus {
    NEW,        // Just added
    LEARNING,   // In the process of memorization
    REVIEWING,  // Repeated with increasing intervals
    MASTERED,   // Remembered
    FORGOTTEN   // Forgotten, returned to the learning process
}