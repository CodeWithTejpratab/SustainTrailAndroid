package com.sustenanceandroid.model.api

import android.util.Log
import com.google.gson.GsonBuilder
import com.sustenanceandroid.model.Exposition
import com.sustenanceandroid.module.AppModule.firebaseAuthManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

enum class Screen {
    HOME,
    EVENTS,
}

class APIRepository {
    companion object {
        private const val BASE_URL = "http://159.89.46.197:8000/"
        private const val TAG = "APP ERROR"
        private val USER_ID: String
            get() = firebaseAuthManager.currentUserId
    }

    private val gson = GsonBuilder()
        .registerTypeAdapter(Exposition::class.java, ExpositionDeserializer())
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    suspend fun registerUser(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.registerUser(USER_ID)
                Log.d(TAG, "Register user response: $USER_ID")
                response.isSuccessful
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.d(TAG, it) }
                false
            }
        }
    }

    suspend fun deleteUser(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.deleteUser(USER_ID)
                response.isSuccessful
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.d(TAG, it) }
                false
            }
        }
    }

    suspend fun sendActionEvent(eventCategory: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.postSustainScore(USER_ID, eventCategory)
                response.isSuccessful
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.d(TAG, it) }
                false
            }
        }
    }

    suspend fun fetchExpositions(screen: Screen): List<Exposition>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = getExpositionType(screen, USER_ID)
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.d(TAG, it) }
                null
            }
        }
    }

    private suspend fun getExpositionType(screen: Screen, userId: String): Response<List<Exposition>> {
        return when (screen) {
            Screen.HOME -> apiService.getHomeExpositions(userId)
            Screen.EVENTS -> apiService.getEventExpositions()
        }
    }
}
