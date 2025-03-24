package com.example.flashword.data.source

import android.util.Log
import com.example.flashword.data.model.CardCreateDto
import com.example.flashword.data.model.CardDto
import com.example.flashword.data.model.DeckCreateDto
import com.example.flashword.data.model.DeckDto
import com.example.flashword.data.model.DeckStatisticsDto
import com.example.flashword.domain.repos.AccountService
import com.google.firebase.Timestamp
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreDataSource @Inject constructor(
    private val auth: AccountService
) {
    private val db = FirebaseFirestore.getInstance()
    private val decksCollection = db.collection("decks")

    fun observeDecks(userId: String, listener: (List<DeckDto>) -> Unit) =
        decksCollection.whereEqualTo("userId", auth.currentUserId)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.e(FIRESTORE_LOG, exception.toString())
                    return@addSnapshotListener
                }

                val decks = snapshot?.documents?.mapNotNull {
                    val deck = it.toObject(DeckDto::class.java)
                    if (deck != null) {
                        deck.deckId = it.id
                        deck
                    } else null
                } ?: emptyList()
                listener(decks)
            }

    fun observeDecks(): Flow<List<DeckDto>> = callbackFlow {
        val listenerRegistration = decksCollection
            .whereEqualTo("userId", auth.currentUserId)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.e(FIRESTORE_LOG, exception.toString())
                    close(exception)
                    return@addSnapshotListener
                }

                val decks = snapshot?.documents?.mapNotNull {
                    val deck = it.toObject(DeckDto::class.java)
                    if (deck != null) {
                        deck.deckId = it.id
                        deck
                    } else null
                } ?: emptyList()

                trySend(decks).isSuccess
            }

        awaitClose { listenerRegistration.remove() }
    }

    suspend fun addDeck(deck: DeckCreateDto) {
        decksCollection.add(deck).await()
    }

    fun addCard(card: CardCreateDto) {
//        val data1 = hashMapOf(
//            "frontText" to "test front 1 review at 21.08.2025 \n\n test1",
//            "backText" to "test back  1 \n" +
//                    "\n" +
//                    " test1",
//            "createdAt" to Timestamp(1737480701, 0),
//            "lastReviewAt" to Timestamp(1747848701, 0),
//            "nextReviewAt" to Timestamp(1755797501, 0),
//            "wasForgotten" to false,
//        )
//        val data2 = hashMapOf(
//            "frontText" to "test front 2 \n\n test2",
//            "backText" to "test back  2 \n" +
//                    "\n" +
//                    " test1",
//            "createdAt" to Timestamp(1737480701, 0),
//            "lastReviewAt" to Timestamp(1740331901, 0),
//            "nextReviewAt" to Timestamp(1740159101, 0),
//            "wasForgotten" to false,
//        )
//        val data3 = hashMapOf(
//            "frontText" to "test front 3 \n\n test3",
//            "backText" to "test back 3 \n" +
//                    "\n" +
//                    " test1",
//            "createdAt" to Timestamp(1737480701, 0),
//            "lastReviewAt" to Timestamp(1740331901, 0),
//            "nextReviewAt" to Timestamp(1740159101, 0),
//            "wasForgotten" to false,
//        )
//
//        decksCollection
//            .document(card.deckId)
//            .collection("cards")
//            .add(data1)
//            .addOnSuccessListener { documentReference ->
//                Log.d(FIRESTORE_LOG, "Card added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.e(FIRESTORE_LOG, e.toString())
//            }
//        decksCollection
//            .document(card.deckId)
//            .collection("cards")
//            .add(data2)
//            .addOnSuccessListener { documentReference ->
//                Log.d(FIRESTORE_LOG, "Card added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.e(FIRESTORE_LOG, e.toString())
//            }
//        decksCollection
//            .document(card.deckId)
//            .collection("cards")
//            .add(data3)
//            .addOnSuccessListener { documentReference ->
//                Log.d(FIRESTORE_LOG, "Card added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.e(FIRESTORE_LOG, e.toString())
//            }

        decksCollection
            .document(card.deckId)
            .collection("cards")
            .add(card)
            .addOnSuccessListener { documentReference ->
                Log.d(FIRESTORE_LOG, "Card added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.e(FIRESTORE_LOG, e.toString())
            }
    }

    fun updateCard(card: CardDto) {
        val updatedCards = hashMapOf<String, Any>(
            "frontText" to card.frontText,
            "backText" to card.backText,
            "lastReviewAt" to card.lastReviewAt,
            "nextReviewAt" to card.nextReviewAt,
            "wasForgotten" to card.wasForgotten,
            "interval" to card.interval,
        )
        Log.d(FIRESTORE_LOG, "--- ${card.deckId}")

        decksCollection
            .document(card.deckId)
            .collection("cards")
            .document(card.cardId)
            .update(updatedCards)
            .addOnSuccessListener {
                Log.d(FIRESTORE_LOG, "Card updated with ID: ${card.cardId} ")
            }
            .addOnFailureListener { e ->
                Log.e(FIRESTORE_LOG, e.toString() + " ${card.deckId}")
            }
    }

    suspend fun getCardsFromDeck(deckId: String): List<CardDto> {
        return try {
            val snapshot = db.collection("decks").document(deckId)
                .collection("cards")
                .get()
                .await()

            snapshot.documents.mapNotNull {
                val card = it.toObject(CardDto::class.java)
                if (card != null) {
                    card.cardId = it.id
                    card
                } else null
            }
        } catch (e: Exception) {
            Log.e(FIRESTORE_LOG, e.toString())
            emptyList()
        }
    }

    suspend fun getCardsForReviewFromDeck(deckId: String, timestamp: Timestamp): List<CardDto> {
        return try {
            val snapshot = db.collection("decks").document(deckId)
                .collection("cards")
                .whereLessThan("nextReviewAt", timestamp)
                .get()
                .await()

            snapshot.documents.mapNotNull {
                val card = it.toObject(CardDto::class.java)
                if (card != null) {
                    card.cardId = it.id
                    card.deckId = deckId
                    card
                } else null
            }
        } catch (e: Exception) {
            Log.e(FIRESTORE_LOG, e.toString())
            emptyList()
        }
    }

    suspend fun getNewCardsCountByDeck(deckId: String): Long {
        return try {
            val result = decksCollection
                .document(deckId)
                .collection("cards")
                .whereLessThan("nextReviewAt", Timestamp.now())
                .whereLessThan("interval", 5 * 60 * 1000) //5 minutes
                .count()
                .get(AggregateSource.SERVER)
                .await()
            result.count
        } catch (e: Exception) {
            Log.d(FIRESTORE_LOG, "Error fetching total count: ", e)
            0L
        }
    }

    suspend fun getLearnCardsCountByDeck(deckId: String): Long {
        return try {
            val result = decksCollection
                .document(deckId)
                .collection("cards")
                .whereLessThan("nextReviewAt", Timestamp.now())
                .whereGreaterThan("interval", 5 * 60 * 1000 + 1)
                .whereLessThan("interval", 10 * 24 * 60 * 60 * 1000) //10 days
                .count()
                .get(AggregateSource.SERVER)
                .await()
            result.count
        } catch (e: Exception) {
            Log.d(FIRESTORE_LOG, "Error fetching total count: ", e)
            0L
        }
    }

    suspend fun getReviewCardsCountByDeck(deckId: String): Long {
        return try {
            val result = decksCollection
                .document(deckId)
                .collection("cards")
                .whereLessThan("nextReviewAt", Timestamp.now())
                .whereGreaterThan("interval", 10 * 24 * 60 * 60 * 1000 + 1)
                .count()
                .get(AggregateSource.SERVER)
                .await()
            result.count
        } catch (e: Exception) {
            Log.d(FIRESTORE_LOG, "Error fetching total count: ", e)
            0L
        }
    }

    suspend fun getCardsCountByDeck(deckId: String): Long {
        return try {
            val result = decksCollection
                .document(deckId)
                .collection("cards")
                .count()
                .get(AggregateSource.SERVER)
                .await()

            result.count
        } catch (e: Exception) {
            Log.d(FIRESTORE_LOG, "Error fetching total count: ", e)
            0L
        }
    }

    fun observeCardsCountByDeck(deckId: String): Flow<Long> = callbackFlow {
        val query = decksCollection
            .document(deckId)
            .collection("cards")

        val listenerRegistration = query.addSnapshotListener { _, _ ->
            query.count().get(AggregateSource.SERVER)
                .addOnSuccessListener { aggregateResult ->
                    Log.d(FIRESTORE_LOG, "cards in snapshot: ${aggregateResult.count}")
                    trySend(aggregateResult.count)
                }
                .addOnFailureListener { exception ->
                    Log.d(FIRESTORE_LOG, "Error fetching total count: ", exception)
                    trySend(0L)
                }
        }

        awaitClose { listenerRegistration.remove() }
    }


    fun observeReviewCardsCountByDeck(deckId: String): Flow<Long> = callbackFlow {
        val query = decksCollection
            .document(deckId)
            .collection("cards")
            .whereLessThan("nextReviewAt", Timestamp.now())
            .whereGreaterThan("interval", 10 * 24 * 60 * 60 * 1000) // 10 дней

        val listenerRegistration = query.addSnapshotListener { _, _ ->
            query.count().get(AggregateSource.SERVER)
                .addOnSuccessListener { aggregateResult ->
                    trySend(aggregateResult.count)
                }
                .addOnFailureListener { exception ->
                    Log.d(FIRESTORE_LOG, "Error fetching review cards count: ", exception)
                    trySend(0L)
                }
        }

        awaitClose { listenerRegistration.remove() }
    }

    fun observeLearnCardsCountByDeck(deckId: String): Flow<Long> = callbackFlow {
        val query = decksCollection
            .document(deckId)
            .collection("cards")
            .whereLessThan("nextReviewAt", Timestamp.now())
            .whereGreaterThan("interval", 5 * 60 * 1000 + 1)
            .whereLessThan("interval", 10 * 24 * 60 * 60 * 1000)

        val listenerRegistration = query.addSnapshotListener { _, _ ->
            query.count().get(AggregateSource.SERVER).addOnSuccessListener { aggregateResult ->
                trySend(aggregateResult.count)
            }.addOnFailureListener {
                Log.d(FIRESTORE_LOG, "Error fetching total count: ", it)
                trySend(0L)
            }
        }

        awaitClose { listenerRegistration.remove() }
    }

    fun observeNewCardsCountByDeck(deckId: String): Flow<Long> = callbackFlow {
        val query = decksCollection
            .document(deckId)
            .collection("cards")
            .whereLessThan("nextReviewAt", Timestamp.now())
            .whereLessThan("interval", 5 * 60 * 1000) // 5 minutes

        val listenerRegistration = query.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.d(FIRESTORE_LOG, "Error fetching new cards count: ", exception)
                trySend(0L)
                return@addSnapshotListener
            }

            snapshot?.let {
                Log.d(FIRESTORE_LOG, "New cards in snapshot: ${snapshot.size()}")

                val count = snapshot.size()
                Log.d(FIRESTORE_LOG, "Fetched new cards count: $count")

                trySend(count.toLong())
            }
        }

        awaitClose { listenerRegistration.remove() }
    }

    suspend fun updateDeckStats(deckStatisticsDto: DeckStatisticsDto) {

        try {
            val querySnapshot = decksCollection
                .document(deckStatisticsDto.deckId)
                .collection("statistics")
                .whereEqualTo("date", deckStatisticsDto.date)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                val docRef = querySnapshot.documents.first().reference
                FirebaseFirestore.getInstance().runTransaction { transaction ->
                    transaction.update(docRef, "reviewedCount", FieldValue.increment(1))
                }
                Log.d(FIRESTORE_LOG, "The doc was found, counter incremented")
            } else {
                val newDocRef = decksCollection
                    .document(deckStatisticsDto.deckId)
                    .collection("statistics")
                    .document()

                val stats = hashMapOf<String, Any>(
                    "deckId" to deckStatisticsDto.deckId,
                    "date" to deckStatisticsDto.date,
                    "cardsDue" to deckStatisticsDto.cardsDue,
                    "reviewedCount" to 1
                )
                FirebaseFirestore.getInstance().runTransaction { transaction ->
                    transaction.set(newDocRef, stats)
                }
                Log.d(FIRESTORE_LOG, "The document wasn't found, created a new one")
            }

            Log.d(FIRESTORE_LOG, "success")
        } catch (e: Exception) {
            Log.e(FIRESTORE_LOG, "Error during operation", e)
        }
    }
}


fun Query.snapshotFlow(): Flow<QuerySnapshot> = callbackFlow {
    val listenerRegistration = addSnapshotListener { value, error ->
        if (error != null) {
            Log.e(FIRESTORE_LOG, error.toString())
            close()
            return@addSnapshotListener
        }
        if (value != null)
            trySend(value)
    }
    awaitClose {
        listenerRegistration.remove()
    }
}

const val FIRESTORE_LOG = "Firestore"