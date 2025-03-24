package com.example.flashword.data.model

import android.util.Log
import com.example.flashword.data.source.FIRESTORE_LOG
import com.example.flashword.domain.model.DeckStatistics
import com.google.firebase.Timestamp

data class DeckStatisticsDto(
    var deckId: String = "",
    val date: Timestamp = Timestamp.now(),
    val cardsDue: Int = 0,
    val reviewedCount: Int = 0,
)

fun DeckStatisticsDto.toDeckStatistics(): DeckStatistics =
    DeckStatistics(
        deckId = deckId,
        date = date.toLong(),
        cardsDue = cardsDue,
        reviewedCount = reviewedCount
    )

fun DeckStatistics.toDeckStatisticsDto(): DeckStatisticsDto {

    try {
        this.date.toTimestamp()
    } catch (e: Exception) {
        Log.e(FIRESTORE_LOG, "4444ERROR+++++ " + this.date + e)
    }
    return DeckStatisticsDto(
        deckId = deckId,
        date = date.toTimestamp(),
        cardsDue = cardsDue,
        reviewedCount = reviewedCount
    )
}
