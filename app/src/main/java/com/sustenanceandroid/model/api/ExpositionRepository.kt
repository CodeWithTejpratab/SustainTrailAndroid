package com.sustenanceandroid.model.api

import android.util.Log
import com.google.gson.GsonBuilder
import com.sustenanceandroid.model.Exposition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ExpositionRepository {
    companion object {
        private const val BASE_URL = "http://10.0.2.2:8000"
    }

    private val gson = GsonBuilder()
        .registerTypeAdapter(Exposition::class.java, ExpositionDeserializer())
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    suspend fun fetchExpositions(): List<Exposition>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getExpositions()
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.d("ERROR", it) }
                null
            }
        }
    }
}
