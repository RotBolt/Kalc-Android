package io.kaendagger.kalcandroid.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class HistoryEntity

data class Date(val dateString: String):HistoryEntity()

@Entity(tableName = "history")
data class Calculation(
    @PrimaryKey
    val timeInMillis:Long,
    val expression:String,
    val result:String
):HistoryEntity()
