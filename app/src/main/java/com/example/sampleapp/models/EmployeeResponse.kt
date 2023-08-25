package com.example.sampleapp.models

data class EmployeeResponse(
    val data: List<Employee>? = null,
    val status: String? = ""
)