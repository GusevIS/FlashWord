package com.example.flashword.domain.usecases

import android.util.Log
import com.example.flashword.data.source.FIRESTORE_LOG
import com.example.flashword.domain.model.CardModel
import com.example.flashword.domain.repos.CardsRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ScheduleNextReviewUseCase @Inject constructor(
    private val cardsRepository: CardsRepository
) {
    operator fun invoke(card: CardModel, recallQuality: RecallQuality) {

        Log.e(FIRESTORE_LOG, "+++++ ${card.deckId}")
        cardsRepository.updateCard(card.updateCardStatusWithIntervals(recallQuality, System.currentTimeMillis()))
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

        return this.copy(
            nextReviewAt = currentTime + nextInterval,
            lastReviewAt = System.currentTimeMillis(),
            wasForgotten = !this.wasForgotten
        )
    }

}

var STARTED_INTERVAL: Long = TimeUnit.MINUTES.toMillis(2)

enum class RecallQuality {
    WRONG,
    HARD,
    EASY
}