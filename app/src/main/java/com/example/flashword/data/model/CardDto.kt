package com.example.flashword.data.model

import com.example.flashword.domain.model.CardModel
import com.example.flashword.domain.model.CardStatus
import com.google.firebase.Timestamp

data class CardDto(
    val deckId: String = "",
    val frontText: String = "",
    val backText: String = "",

    val createdAt: Timestamp = Timestamp.now(),
    val lastReviewAt: Timestamp = Timestamp.now(),
    val nextReviewAt: Timestamp = Timestamp.now(),

    val status: String = "",
)

fun CardModel.toCardDto() =
    CardDto(
        deckId = deckId,
        frontText = frontText,
        backText = backText,
        createdAt = createdAt.toTimestamp(),
        lastReviewAt = lastReviewAt.toTimestamp(),
        nextReviewAt = nextReviewAt.toTimestamp(),
        status = when (status) {
            CardStatus.NEW -> "NEW"
            CardStatus.LEARNING -> "LEARNING"
            CardStatus.REVIEWING -> "REVIEWING"
            CardStatus.MASTERED -> "MASTERED"
            CardStatus.FORGOTTEN -> "FORGOTTEN"
        }
    )

fun CardDto.toCardModel() =
    CardModel(
        deckId = deckId,
        frontText = frontText,
        backText = backText,
        createdAt = createdAt.toLong(),
        lastReviewAt = lastReviewAt.toLong(),
        nextReviewAt = nextReviewAt.toLong(),
        status = when (status) {
            "NEW" -> CardStatus.NEW
            "LEARNING" -> CardStatus.LEARNING
            "REVIEWING" -> CardStatus.REVIEWING
            "MASTERED" -> CardStatus.MASTERED
            "FORGOTTEN" -> CardStatus.FORGOTTEN
            else -> CardStatus.NEW
        }
    )

enum class CardStatus {
    NEW,        // Just added
    LEARNING,   // In the process of memorization
    REVIEWING,  // Repeated with increasing intervals
    MASTERED,   // Remembered
    FORGOTTEN   // Forgotten, returned to the learning process
}