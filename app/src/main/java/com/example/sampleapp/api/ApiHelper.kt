package com.example.sampleapp.api

import com.example.sampleapp.models.EmployeeResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun getUsers(): Response<EmployeeResponse>
}