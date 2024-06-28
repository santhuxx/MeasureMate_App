package com.example.measuremate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.measuremate.database.MeasureDatabase
import com.example.measuremate.repository.MeasureRepository
import com.example.measuremate.viewmodel.MeasureViewModel
import com.example.measuremate.viewmodel.MeasureViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var measureViewModel: MeasureViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
    }

    private fun setupViewModel(){
        val measureRepository = MeasureRepository(MeasureDatabase(this))
        val viewModelProviderFactory = MeasureViewModelFactory(application, measureRepository)
        measureViewModel = ViewModelProvider(this, viewModelProviderFactory)[MeasureViewModel::class.java]

    }
}