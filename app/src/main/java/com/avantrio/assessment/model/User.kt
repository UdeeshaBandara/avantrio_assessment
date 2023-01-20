package com.avantrio.assessment.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//class User : ArrayList<UserItem>()

@Entity(tableName = "UserItem")
data class User(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "isFav") @Transient val isFav: Boolean
)