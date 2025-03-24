package com.example.flashword.data.remote

import android.util.Log
import com.example.flashword.data.model.DeckDto
import com.example.flashword.data.model.toDeckCreateDto
import com.example.flashword.data.model.toDeckDto
import com.example.flashword.data.model.toDeckModel
import com.example.flashword.data.source.FirestoreDataSource
import com.example.flashword.domain.model.DeckCreateModel
import com.example.flashword.domain.model.DeckModel
import com.example.flashword.domain.repos.DecksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DecksRepositoryImpl @Inject constructor(
    private val firestore: FirestoreDataSource,
): DecksRepository {
    override suspend fun addDeck(deck: DeckCreateModel) {
        firestore.addDeck(deck.toDeckCreateDto())
    }

    override fun observeDecks(userId: String, listener: (List<DeckModel>) -> Unit) =
        firestore.observeDecks(userId) { deckModelList ->
            val deckDtoList = deckModelList.map { it.toDeckModel() }
            listener(deckDtoList)
        }

    override fun observeDecks(): Flow<List<DeckModel>> = flow {
        val decksDtoFlow = firestore.observeDecks()

        decksDtoFlow.collect { listDto ->
            val deckModels = mutableListOf<DeckModel>()

            for (deckDto in listDto) {
                val processedDeck = coroutineScope {
                    val deckModel = deckDto.toDeckModel()

                    val newCardsDeferred = async(Dispatchers.IO) { firestore.getNewCardsCountByDeck(deckDto.deckId) }
                    val learnCardsDeferred = async(Dispatchers.IO) { firestore.getLearnCardsCountByDeck(deckDto.deckId) }
                    val reviewCardsDeferred = async(Dispatchers.IO) { firestore.getReviewCardsCountByDeck(deckDto.deckId) }
                    val totalDeferred = async(Dispatchers.IO) { firestore.getCardsCountByDeck(deckDto.deckId) }

                    val newCardsResult = newCardsDeferred.await()
                    val learnCardsResult = learnCardsDeferred.await()
                    val reviewCardsResult = reviewCardsDeferred.await()
                    val totalResult = totalDeferred.await()

                    deckModel.copy(
                        newCards = newCardsResult,
                        cardsToStudy = learnCardsResult,
                        cardsToReview = reviewCardsResult,
                        todayDue = newCardsResult + learnCardsResult + reviewCardsResult,
                        totalCards = totalResult
                    )
                }
                deckModels.add(processedDeck)
            }

            emit(deckModels)
        }
    }.flowOn(Dispatchers.IO)

//    @OptIn(ExperimentalCoroutinesApi::class)
//    override fun observeDecks(): Flow<List<DeckModel>> =
//        firestore.observeDecks().flatMapLatest { deckDtoList ->
//            val deckFlows = deckDtoList.map { deckDto ->
//                observeDeckStats(deckDto.deckId).map { stats ->
//                    deckDto.toDeckModel().copy(
//                        newCards = stats.newCards,
//                        cardsToStudy = stats.learnCards,
//                        cardsToReview = stats.reviewCards,
//                        todayDue = stats.newCards + stats.learnCards + stats.reviewCards,
//                        totalCards = stats.totalCards
//                    )
//                }
//            }
//            combine(deckFlows) { it.toList() }
//        }
//
//    data class DeckStats(
//        val newCards: Long = 0L,
//        val learnCards: Long = 0L,
//        val reviewCards: Long = 0L,
//        val totalCards: Long = 0L
//    )
//
//    fun observeDeckStats(deckId: String): Flow<DeckStats> {
//
//        val learnCardsFlow = firestore.observeLearnCardsCountByDeck(deckId)
//        val reviewCardsFlow = firestore.observeReviewCardsCountByDeck(deckId)
//        val totalCardsFlow = firestore.observeCardsCountByDeck(deckId)
//        val newCardsFlow = firestore.observeNewCardsCountByDeck(deckId)
//
//        return combine(learnCardsFlow, reviewCardsFlow, totalCardsFlow, newCardsFlow) { learn, review, total, new ->
//            Log.d("Firestore", "Stats changed: deckId=$deckId new=$new learn=$learn review=$review total=$total")
//            DeckStats(
//                newCards = new,
//                learnCards = learn,
//                reviewCards = review,
//                totalCards = total
//            )
//        }
//    }
//    override fun observeDecks(): Flow<List<DeckModel>> =
//        firestore.observeDecks().map { deckDtoList ->
//            deckDtoList.map { deckDto ->
//                var deckModel = deckDto.toDeckModel()
//                val newCards = firestore.getNewCardsCountByDeck(deckDto.deckId)
//                val learnCards = firestore.getLearnCardsCountByDeck(deckDto.deckId)
//                val reviewCards = firestore.getReviewCardsCountByDeck(deckDto.deckId)
//                val total = firestore.getCardsCountByDeck(deckDto.deckId)
//                Log.d("Firestore ", "--- " + total)
//                deckModel = deckModel.copy(
//                    newCards = newCards,
//                    cardsToStudy = learnCards,
//                    cardsToReview = reviewCards,
//                    todayDue = newCards + learnCards + reviewCards,
//                    totalCards = total
//                )
//
//                deckModel
//            }
//        }
}