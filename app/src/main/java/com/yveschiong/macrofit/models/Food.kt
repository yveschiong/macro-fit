package com.yveschiong.macrofit.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "food")
data class Food(
    @ColumnInfo(name = "time_added")
    var timeAdded: Long,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "amount")
    var amount: Float,

    @ColumnInfo(name = "unit")
    @Weight.Unit
    var unit: String,

    @ColumnInfo(name = "calories")
    var calories: Float,

    @ColumnInfo(name = "protein")
    var protein: Float,

    @ColumnInfo(name = "carbs")
    var carbs: Float,

    @ColumnInfo(name = "fat")
    var fat: Float,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
) : Parcelable