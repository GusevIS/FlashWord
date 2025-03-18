package com.example.flashword.data.model

import com.google.firebase.Timestamp

fun Timestamp.toLong(): Long {
    return this.seconds * 1000 + this.nanoseconds / 1000000
}

fun Long.toTimestamp(): Timestamp {
    return Timestamp(this / 1000, ((this % 1000) * 1000000).toInt())
}