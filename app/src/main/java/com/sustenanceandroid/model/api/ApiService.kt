package com.sustenanceandroid.model.api

import com.sustenanceandroid.model.Exposition
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/home/123")
    suspend fun getHomeExpositions(): Response<List<Exposition>>

    @GET
    suspend fun getEventExpositions(): Response<List<Exposition>>

    @DELETE("user/delete/{user_id}")
    suspend fun deleteUser(@Path("user_id") userId: String): Response<Unit>
}
