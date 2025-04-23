package com.sustenanceandroid.model.api

import com.sustenanceandroid.model.Exposition
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("api/home/{userId}")
    suspend fun getHomeExpositions(@Path("userId") userId: String): Response<List<Exposition>>

    @GET("api/events/{userId}")
    suspend fun getEventExpositions(@Path("userId") userId: String): Response<List<Exposition>>

    @DELETE("api/user/{userId}")
    suspend fun deleteUser(@Path("userId") userId: String): Response<Unit>

    @PUT("api/sustainscore/{userId}/{eventCategory}")
    suspend fun postSustainScore(@Path("userId") userId: String, @Path("eventCategory") eventCategory: String): Response<Unit>
}
