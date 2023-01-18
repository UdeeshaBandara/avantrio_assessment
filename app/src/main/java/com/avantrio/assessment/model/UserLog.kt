package com.avantrio.assessment.model

data class UserLog(
    val logs: List<Log>,
    val user: String,
    val user_id: Int
)

data class Log(
    val alert_view: Boolean,
    val date: String,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val time: String
)