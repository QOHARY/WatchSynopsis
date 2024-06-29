package com.example.uas.watchsynopsis.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_data_table", indices = [Index(value=["user_email"],unique= true)])
data class  User(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val id: Int = 0,

    @ColumnInfo(name = "user_name")
    var userName: String,

    @ColumnInfo(name = "user_email")
    var email: String,

    @ColumnInfo(name = "user_password")
    var password: String

)
