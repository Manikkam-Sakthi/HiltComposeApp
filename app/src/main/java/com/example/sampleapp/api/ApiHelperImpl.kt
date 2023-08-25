package com.example.sampleapp.api

import com.example.sampleapp.models.EmployeeResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun getUsers(): Response<EmployeeResponse> = apiService.getUsers()
}