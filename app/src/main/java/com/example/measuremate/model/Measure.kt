package com.example.measuremate.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "measure")
@Parcelize
data class Measure(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val cusName: String,
    val cusMobile: String,
    val cloth: String,
    val orderedDate: String,
    val deliveryDate: String,
    val measurements: String

): Parcelable
