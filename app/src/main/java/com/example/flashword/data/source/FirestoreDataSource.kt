package com.example.flashword.data.source

import android.util.Log
import com.example.flashword.data.model.CardCreateDto
import com.example.flashword.data.model.CardDto
import com.example.flashword.data.model.DeckCreateDto
import com.example.flashword.data.model.DeckDto
import com.example.flashword.domain.repos.AccountService
import com.google.firebase.Timestamp
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
        decksCollection.whereEqualTo("userId", auth.currentUserId).addSnapshotListener { snapshot, exception ->
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
            "wasForgotten" to card.wasForgotten
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