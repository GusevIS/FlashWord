package com.example.flashword.data.source

import com.example.flashword.domain.model.DeckModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreDataSource @Inject constructor() {
    private val db = FirebaseFirestore.getInstance()
    private val decksCollection = db.collection("decks")

    fun observeDecks(userId: String, listener: (List<DeckModel>) -> Unit) =
        decksCollection.whereEqualTo("userId", userId).addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                //TODO
                return@addSnapshotListener
            }

            val decks = snapshot?.documents?.mapNotNull {
                val deck = it.toObject(DeckModel::class.java)
                if (deck != null) {
                    deck.deckId = it.id
                    deck
                } else null
            } ?: emptyList()
            listener(decks)
        }


    suspend fun addDeck(deck: DeckModel) {
        decksCollection.add(deck).await()
    }
}