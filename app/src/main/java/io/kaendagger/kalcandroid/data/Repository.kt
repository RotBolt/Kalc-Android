package io.kaendagger.kalcandroid.data

import android.app.Application
import io.kaendagger.kalcandroid.data.HistoryDB
import io.kaendagger.kalcandroid.data.entity.Calculation

class Repository(application: Application) {

    private val db = HistoryDB.getDatabase(application)
    private val historyDao = db.historyDao()

    fun getAllCalculations() = historyDao.getHistory()

    suspend fun insertCalculation(calculation: Calculation) = historyDao.insertCalculation(calculation)
}