package io.kaendagger.kalcandroid.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.kaendagger.kalcandroid.data.entity.Calculation

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history ORDER BY timeInMillis DESC")
    fun getHistory():LiveData<List<Calculation>>

    @Insert
    suspend fun insertCalculation(calculation: Calculation)
}