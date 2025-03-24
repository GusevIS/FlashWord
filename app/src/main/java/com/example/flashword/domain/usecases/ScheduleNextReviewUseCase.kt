package com.example.flashword.domain.usecases

import android.util.Log
import com.example.flashword.data.source.FIRESTORE_LOG
import com.example.flashword.domain.model.CardModel
import com.example.flashword.domain.model.DeckStatistics
import com.example.flashword.domain.repos.CardsRepository
import com.example.flashword.domain.repos.DeckStatisticsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ScheduleNextReviewUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val deckStatisticsRepository: DeckStatisticsRepository
) {
    suspend operator fun invoke(card: CardModel, recallQuality: RecallQuality) = withContext(Dispatchers.IO) {

        Log.e(FIRESTORE_LOG, "111+++++ ${card.deckId}")
        cardsRepository.updateCard(card.updateCardStatusWithIntervals(recallQuality, System.currentTimeMillis()))
        deckStatisticsRepository.updateDeckStats(DeckStatistics(
            card.deckId,
            getTodayStartInNanos(),
            0,
            1,
        ))
    }


    private fun CardModel.updateCardStatusWithIntervals(recallQuality: RecallQuality, currentTime: Long): CardModel {
        val coefficient: Long = if (this.wasForgotten) 5L else 1L
        val lastInterval = this.nextReviewAt - this.lastReviewAt
        val nextInterval: Long = if (lastInterval <= 0) STARTED_INTERVAL
            else {
                when (recallQuality) {
                    RecallQuality.EASY -> lastInterval * 5L * coefficient
                    RecallQuality.HARD -> lastInterval * 3L * coefficient
                    RecallQuality.WRONG -> STARTED_INTERVAL
                }
        }
        val lastReviewAt = System.currentTimeMillis()
        val nextReviewAt = currentTime + nextInterval

            return this.copy(
                nextReviewAt = nextReviewAt,
                lastReviewAt = lastReviewAt,
                wasForgotten = !this.wasForgotten,
                interval = nextReviewAt - lastReviewAt
            )
    }

}

var STARTED_INTERVAL: Long = TimeUnit.MINUTES.toMillis(2)

fun getTodayStartInNanos(): Long {
    val calendar = Calendar.getInstance()

    // Установим время на полночь (00:00:00) в локальной временной зоне
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    // Возвращаем время в миллисекундах
    return calendar.timeInMillis
}

enum class RecallQuality {
    WRONG,
    HARD,
    EASY
}