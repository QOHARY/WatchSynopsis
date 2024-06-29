package com.example.uas.watchsynopsis.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)

    @Update
    suspend fun update(user: User)

    @Query("SELECT user_name FROM user_data_table WHERE user_email = :email")
    suspend fun getUserNameByEmail(email: String): String?

    @Query("SELECT * FROM user_data_table WHERE user_email = :email AND user_password = :password")
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?

}