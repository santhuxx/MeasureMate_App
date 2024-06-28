package com.example.measuremate.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.measuremate.repository.MeasureRepository

class MeasureViewModelFactory(val app: Application, private val measureRepository: MeasureRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MeasureViewModel(app, measureRepository) as T
    }
}