package com.example.measuremate.repository

import com.example.measuremate.database.MeasureDatabase
import com.example.measuremate.model.Measure

class MeasureRepository (private val db: MeasureDatabase){

    suspend fun  insertMeasure(measure: Measure) = db.getMeasureDao().insertMeasure(measure)
    suspend fun  deleteMeasure(measure: Measure) = db.getMeasureDao().deleteMeasure(measure)
    suspend fun  updateMeasure(measure: Measure) = db.getMeasureDao().updateMeasure(measure)

    fun getAllMeasures() = db.getMeasureDao().getAllMeasures()
    fun searchMeasure(query: String?) = db.getMeasureDao().searchMeasure(query)

}