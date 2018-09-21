package com.yveschiong.macrofit.constants

object Constants {
    const val DATABASE_NAME = "macro-fit-db"

    const val RESULT_KEY = "result_key"

    const val EXTRA_DAY_TIMESTAMP = "extra_day_timestamp"
    const val EXTRA_NUTRITION_FACT = "extra_nutrition_fact"
    const val EXTRA_FOOD = "extra_food"
    const val EXTRA_SHOULD_DELETE = "extra_should_delete"

    const val REQUEST_CODE_ADD_NUTRITION_FACT = 1
    const val REQUEST_CODE_EDIT_NUTRITION_FACT = 2
    const val REQUEST_CODE_ADD_FOOD = 3
    const val REQUEST_CODE_EDIT_FOOD = 4

    const val DB_TESTING = true

    // Base url for the usda search api
    const val BASE_URL = "https://api.nal.usda.gov/ndb/"

    // Usda Api Key
    const val API_KEY = "PRDHVl96gkByT2fSrxp2znM722a2QeHYjB2dbmvy"
}