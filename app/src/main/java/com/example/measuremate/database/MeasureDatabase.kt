package com.example.measuremate.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.measuremate.model.Measure

@Database(entities = [Measure::class], version = 1)
abstract class MeasureDatabase: RoomDatabase() {

    abstract fun getMeasureDao(): MeasureDao

    companion object{
        @Volatile
        private var instance: MeasureDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?:
            createDatabase(context).also{
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MeasureDatabase::class.java,
                "measure_db"
            ).build()

    }

}