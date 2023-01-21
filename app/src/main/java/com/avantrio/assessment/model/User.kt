package com.avantrio.assessment.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "UserItem")
data class User(
    @PrimaryKey val name: String,
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "isFav") @Transient var isFav: Boolean = false,
    @ColumnInfo(name = "monogramColor") var monogramColor: Int
)