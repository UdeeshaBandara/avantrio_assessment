package com.avantrio.assessment.room


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.avantrio.assessment.model.Log
import com.avantrio.assessment.model.User

@Dao
interface UserDao {

    @Insert
    fun insertAll(users: List<User>?): LongArray?

    @Query("UPDATE UserItem SET isFav = :isFav WHERE id = :userId")
    fun changeFavStatus(userId: Int, isFav : Boolean): Int

    @Insert
    fun insertAllUserLogs(users: List<Log>?): LongArray?


}