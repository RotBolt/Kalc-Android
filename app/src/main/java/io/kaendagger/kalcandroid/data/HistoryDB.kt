package io.kaendagger.kalcandroid.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.kaendagger.kalcandroid.data.dao.HistoryDao
import io.kaendagger.kalcandroid.data.entity.Calculation

@Database(entities = [Calculation::class], version = 1)
abstract class HistoryDB : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: HistoryDB? = null

        fun getDatabase(context: Context): HistoryDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDB::class.java,
                    "History_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}