package com.sustenanceandroid.model.api

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.sustenanceandroid.model.AdPlus
import com.sustenanceandroid.model.EventSection
import com.sustenanceandroid.model.Exposition
import com.sustenanceandroid.model.NewsSection
import com.sustenanceandroid.model.ScoreData
import java.lang.reflect.Type

class ExpositionDeserializer : JsonDeserializer<Exposition> {
    private val typeMapping = mapOf(
        "Graph" to ScoreData::class.java,
        "News" to NewsSection::class.java,
        "AD" to AdPlus::class.java,
        "Event" to EventSection::class.java
    )

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Exposition {
        val jsonObject = json.asJsonObject
        val type = jsonObject.get("type")?.asString ?: throw JsonParseException("Missing Type field in Exposition")
        val mappedClass = typeMapping[type] ?: throw JsonParseException("Unknown Exposition type: $type")
        return Gson().fromJson(jsonObject, mappedClass)
    }
}
