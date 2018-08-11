package com.yveschiong.macrofit.models

data class Food (
    var timestamp: Long,
    var name: String,
    var amount: Float,
    var calories: Float,
    var protein: Float,
    var carbs: Float,
    var fat: Float
)