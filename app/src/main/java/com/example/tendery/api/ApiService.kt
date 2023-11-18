package com.example.tendery.api

import com.example.tendery.api.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/")
    fun createAnswer(
        @Body request: RequestBody
    ): Call<CreateResponse>

    @POST("/")
    fun getAnswer(
        @Body request: RequestBody
    ): Call<JawabanResponse>

}