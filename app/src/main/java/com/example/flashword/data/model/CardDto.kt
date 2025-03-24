package com.example.flashword.data.model

import com.example.flashword.domain.model.CardCreateModel
import com.example.flashword.domain.model.CardModel
import com.google.firebase.Timestamp

data class CardCreateDto(
    val deckId: String = "",
    val frontText: String = "",
    val backText: String = "",

    val createdAt: Timestamp = Timestamp.now(),
    val lastReviewAt: Timestamp = Timestamp.now(),
    val nextReviewAt: Timestamp = Timestamp.now(),
    val interval: Long = 0L,

    val wasForgotten: Boolean = false,
)

data class CardDto(
    var cardId: String = "",
    var deckId: String = "",
    val frontText: String = "",
    val backText: String = "",

    val createdAt: Timestamp = Timestamp.now(),
    val lastReviewAt: Timestamp = Timestamp.now(),
    val nextReviewAt: Timestamp = Timestamp.now(),
    val interval: Long = 0L,

    val wasForgotten: Boolean = false,
)

fun CardCreateModel.toCardCreateDto(): CardCreateDto {
    return CardCreateDto(
        deckId = deckId,
        frontText = frontText.replace("\n", "\\n"),
        backText = backText.replace("\n", "\\n"),
        createdAt = createdAt.toTimestamp(),
        lastReviewAt = lastReviewAt.toTimestamp(),
        nextReviewAt = nextReviewAt.toTimestamp(),
        interval = interval,
        wasForgotten = wasForgotten
    )
}

fun CardCreateDto.toCardCreateModel() =
    CardCreateModel(
        deckId = deckId,
        frontText = frontText.replace("\\n", "\n"),
        backText = backText.replace("\\n", "\n"),
        createdAt = createdAt.toLong(),
        lastReviewAt = lastReviewAt.toLong(),
        nextReviewAt = nextReviewAt.toLong(),
        interval = interval,
        wasForgotten = wasForgotten
    )

fun CardModel.toCardDto(): CardDto {
    return CardDto(
        cardId = cardId,
        deckId = deckId,
        frontText = frontText.replace("\n", "\\n"),
        backText = backText.replace("\n", "\\n"),
        createdAt = createdAt.toTimestamp(),
        lastReviewAt = lastReviewAt.toTimestamp(),
        nextReviewAt = nextReviewAt.toTimestamp(),
        interval = interval,
        wasForgotten = wasForgotten
    )
}

fun CardDto.toCardModel() =
    CardModel(
        cardId = cardId,
        deckId = deckId,
        frontText = frontText.replace("\\n", "\n"),
        backText = backText.replace("\\n", "\n"),
        createdAt = createdAt.toLong(),
        lastReviewAt = lastReviewAt.toLong(),
        nextReviewAt = nextReviewAt.toLong(),
        interval = interval,
        wasForgotten = wasForgotten
    )