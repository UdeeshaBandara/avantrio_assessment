package com.avantrio.assessment.model

import androidx.room.ColumnInfo
import androidx.room.Entity

data class UserLog(
    var logs: List<Log>,
    val user: String,
    val user_id: Int
)


@Entity(tableName = "UserLog", primaryKeys = ["id", "userName"])
data class Log(
    @ColumnInfo(name = "alert_view") val alert_view: Boolean,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "userName") @Transient var userName: String,
    @ColumnInfo(name = "isCalculated") @Transient var isCalculated: Boolean,
    @ColumnInfo(name = "distance") @Transient var distance: Double,
    var isLocationViewed: Boolean,
)