package com.sustenanceandroid.model.api

import com.sustenanceandroid.model.Exposition
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api/home/123")
    suspend fun getHomeExpositions(): Response<List<Exposition>>

    @GET
    suspend fun getEventExpositions(): Response<List<Exposition>>
}
