package com.yveschiong.macrofit.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "nutrition_facts",
    indices = [Index(value = ["id", "name", "unit"], name = "index_nutrition_facts_id_name_unit", unique = true)]
)
data class NutritionFact(
    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "amount")
    var amount: Float,

    @Weight.Unit
    @ColumnInfo(name = "unit")
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