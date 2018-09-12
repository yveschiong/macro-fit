package com.yveschiong.macrofit.models

import android.arch.persistence.room.*
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "food",
    indices = [Index(value = ["fact_id", "name", "unit"], name = "index_food_fact_id_name_unit")],
    foreignKeys = [ForeignKey(entity = NutritionFact::class,
        parentColumns = ["id", "name", "unit"],
        childColumns = ["fact_id", "name", "unit"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE)]
)
data class Food(
    @ColumnInfo(name = "day_timestamp")
    var dayTimestamp: Long = 0,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "amount")
    var amount: Float = 0.0f,

    @ColumnInfo(name = "unit")
    @Weight.Unit
    var unit: String = Weight.GRAMS,

    @ColumnInfo(name = "calories")
    var calories: Float = 0.0f,

    @ColumnInfo(name = "protein")
    var protein: Float = 0.0f,

    @ColumnInfo(name = "carbs")
    var carbs: Float = 0.0f,

    @ColumnInfo(name = "fat")
    var fat: Float = 0.0f,

    @ColumnInfo(name = "fact_id")
    var factId: Int = 0,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
) : Parcelable {
    @Ignore
    constructor() : this(0, "", 0.0f, Weight.GRAMS, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0)
}