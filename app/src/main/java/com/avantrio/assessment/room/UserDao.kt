package com.avantrio.assessment.room


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.avantrio.assessment.model.Log
import com.avantrio.assessment.model.User

@Dao
interface UserDao {

    @Insert
    fun insert(users: User)

    @Query("SELECT * FROM UserItem")
    fun selectAllExistingUsers(): List<User>

    @Query("SELECT * FROM UserLog")
    fun selectAllUserLog(): List<Log>

    @Query("UPDATE UserItem SET isFav = :isFav WHERE name = :name")
    fun changeFavStatus(name: String, isFav: Boolean): Int

    @Insert
    fun insertUserLogs(userLog: Log)

    @Query("SELECT COUNT(*) FROM UserLog")
    fun checkUserLogCount(): Int

    @Query("UPDATE UserLog SET distance = :distance, isCalculated = 1 WHERE id = :id AND userName = :userName")
    fun updateDistanceById(id: Int, userName: String, distance: Double): Int

    @Query("UPDATE UserLog SET distance = 0, isCalculated = 0")
    fun resetCalculatedDistance(): Int

}