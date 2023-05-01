package com.example.kin_si.utils

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository {
    private val firestoreDB: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun getSavedCategories(): CollectionReference {
        var collectionReference = firestoreDB.collection("Categories")
        return collectionReference
    }

    fun getSavedDiscover(): CollectionReference {
        var collectionReference = firestoreDB.collection("Discover")
        return collectionReference
    }
}