package com.sustenanceandroid.model.api

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepository {
    companion object {
        private const val BASE_URL = "http://10.0.2.2:8000"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    suspend fun deleteUser(userId: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.deleteUser(userId)
                response.isSuccessful
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.d("ERROR", it) }
                false
            }
        }
    }
}
