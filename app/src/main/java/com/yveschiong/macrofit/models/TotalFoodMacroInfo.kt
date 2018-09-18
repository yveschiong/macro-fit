package com.yveschiong.macrofit.models

import android.arch.persistence.room.ColumnInfo
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TotalFoodMacroInfo(
    @ColumnInfo(name = "calories")
    var calories: Float = 0.0f,

    @ColumnInfo(name = "protein")
    var protein: Float = 0.0f,

    @ColumnInfo(name = "carbs")
    var carbs: Float = 0.0f,

    @ColumnInfo(name = "fat")
    var fat: Float = 0.0f) : Parcelable