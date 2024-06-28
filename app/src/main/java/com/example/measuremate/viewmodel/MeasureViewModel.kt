package com.example.measuremate.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.measuremate.model.Measure
import com.example.measuremate.repository.MeasureRepository
import kotlinx.coroutines.launch

class MeasureViewModel(app: Application, private val measureRepository: MeasureRepository): AndroidViewModel(app) {

    fun addMeasure(measure: Measure)=
        viewModelScope.launch {
            measureRepository.insertMeasure(measure)
        }
    fun deleteMeasure(measure: Measure)=
        viewModelScope.launch {
            measureRepository.deleteMeasure(measure)
        }
    fun updateMeasure(measure: Measure)=
        viewModelScope.launch {
            measureRepository.updateMeasure(measure)
        }

    fun getAllMeasures() = measureRepository.getAllMeasures()

    fun searchMeasure(query: String?)=
        measureRepository.searchMeasure((query))
}