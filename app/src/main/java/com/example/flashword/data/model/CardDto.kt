package com.example.flashword.data.model

import android.util.Log
import com.example.flashword.domain.model.CardCreateModel
import com.example.flashword.domain.model.CardModel
import com.example.flashword.domain.model.CardStatus
import com.google.firebase.Timestamp

data class CardCreateDto(
    val deckId: String = "",
    val frontText: String = "",
    val backText: String = "",

    val createdAt: Timestamp = Timestamp.now(),
    val lastReviewAt: Timestamp = Timestamp.now(),
    val nextReviewAt: Timestamp = Timestamp.now(),

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
        wasForgotten = wasForgotten
//        status = when (status) {
//            CardStatus.NEW -> "NEW"
//            CardStatus.LEARNING -> "LEARNING"
//            CardStatus.REVIEWING -> "REVIEWING"
//            CardStatus.MASTERED -> "MASTERED"
//            CardStatus.FORGOTTEN -> "FORGOTTEN"
//        }
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
        wasForgotten = wasForgotten
//        status = when (status) {
//            "NEW" -> CardStatus.NEW
//            "LEARNING" -> CardStatus.LEARNING
//            "REVIEWING" -> CardStatus.REVIEWING
//            "MASTERED" -> CardStatus.MASTERED
//            "FORGOTTEN" -> CardStatus.FORGOTTEN
//            else -> CardStatus.NEW
//        }
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
        wasForgotten = wasForgotten
//        status = when (status) {
//            CardStatus.NEW -> "NEW"
//            CardStatus.LEARNING -> "LEARNING"
//            CardStatus.REVIEWING -> "REVIEWING"
//            CardStatus.MASTERED -> "MASTERED"
//            CardStatus.FORGOTTEN -> "FORGOTTEN"
//        }
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
        wasForgotten = wasForgotten
//        status = when (status) {
//            "NEW" -> CardStatus.NEW
//            "LEARNING" -> CardStatus.LEARNING
//            "REVIEWING" -> CardStatus.REVIEWING
//            "MASTERED" -> CardStatus.MASTERED
//            "FORGOTTEN" -> CardStatus.FORGOTTEN
//            else -> CardStatus.NEW
//        }
    )