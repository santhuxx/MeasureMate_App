package com.example.measuremate.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.measuremate.model.Measure


@Dao
interface MeasureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeasure(measure: Measure)

    @Update
    suspend fun updateMeasure(measure: Measure)

    @Delete
    suspend fun deleteMeasure(measure: Measure)

    @Query("SELECT * FROM MEASURE ORDER BY id DESC")
    fun getAllMeasures(): LiveData<List<Measure>>

    @Query("SELECT * FROM MEASURE WHERE cusName LIKE :query OR cusMobile LIKE :query")
    fun searchMeasure(query: String?): LiveData<List<Measure>>



}