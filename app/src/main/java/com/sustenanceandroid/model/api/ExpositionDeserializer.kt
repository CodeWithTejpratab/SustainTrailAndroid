package com.sustenanceandroid.model.api

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.sustenanceandroid.model.Exposition
import com.sustenanceandroid.model.LatestNews
import com.sustenanceandroid.model.ScoreData
import java.lang.reflect.Type

class ExpositionDeserializer : JsonDeserializer<Exposition> {
    private val typeMapping = mapOf(
        "data" to ScoreData::class.java,
        "articles" to LatestNews::class.java
    )

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Exposition {
        val jsonObject = json.asJsonObject
        typeMapping.forEach { (key, expoClass) ->
            if (jsonObject.has(key)) return Gson().fromJson(jsonObject, expoClass)
        }
        throw JsonParseException("Unknown Exposition type: $jsonObject")
    }
}
