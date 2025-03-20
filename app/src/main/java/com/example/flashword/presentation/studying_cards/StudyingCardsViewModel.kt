package com.example.flashword.presentation.studying_cards

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.flashword.FlashAppViewModel
import com.example.flashword.domain.model.CardModel
import com.example.flashword.domain.usecases.AddNewCardUseCase
import com.example.flashword.domain.usecases.GetCardsByDeckUseCase
import com.example.flashword.domain.usecases.RecallQuality
import com.example.flashword.domain.usecases.ScheduleNextReviewUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudyingCardsViewModel (
    private val deckId: String,
    private val deckTitle: String,
    private val getCardsByDeckUseCase: GetCardsByDeckUseCase,
    private val scheduleNextReviewUseCase: ScheduleNextReviewUseCase
): FlashAppViewModel() {
    private val _state = MutableStateFlow(StudyingCardsState(deckId, deckTitle))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = _state.value.copy(cards = getCardsByDeckUseCase(deckId))

        }
    }

    fun processCardAnswer(card: CardModel, recall: RecallQuality) {
        Log.d("ewfea", recall.toString())
        //scheduleNextReviewUseCase(card, recall)
    }


    class StudyingCardsViewModelFactory @AssistedInject constructor(
        @Assisted("deckId") private val deckId: String,
        @Assisted("deckTitle") private val deckTitle: String,
        private val getCardsByDeckUseCase: GetCardsByDeckUseCase,
        private val scheduleNextReviewUseCase: ScheduleNextReviewUseCase
    ): ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass.isAssignableFrom(StudyingCardsViewModel::class.java))
            return StudyingCardsViewModel(deckId, deckTitle, getCardsByDeckUseCase, scheduleNextReviewUseCase) as T
        }

        @AssistedFactory
        interface Factory {

            fun create(@Assisted("deckId") deckId: String, @Assisted("deckTitle") deckTitle: String): StudyingCardsViewModelFactory

        }
    }
}