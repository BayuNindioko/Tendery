package com.example.tendery.api

data class CreateResponse(
    val status :String,
    val message : Any
)
data class RequestBody(
    val method: String,
    val params: List<String>
)
