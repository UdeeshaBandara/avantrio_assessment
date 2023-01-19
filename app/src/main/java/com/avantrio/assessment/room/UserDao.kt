package com.avantrio.assessment.room


import androidx.room.Dao
import androidx.room.Insert
import com.avantrio.assessment.model.UserItem

@Dao
interface UserDao {

    @Insert
    fun insertAll(vararg user: UserItem)



}