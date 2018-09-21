package com.yveschiong.macrofit.network.search

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type


class SearchDeserializer : JsonDeserializer<SearchResultList> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): SearchResultList? {
        // Get the element from the parsed JSON
        val content = json?.asJsonObject?.get("list")?.asJsonObject

        // Deserialize it. Use a new instance of Gson to avoid infinite recursion to this deserializer
        return Gson().fromJson(content, typeOfT)
    }
}