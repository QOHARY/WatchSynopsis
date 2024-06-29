package com.example.uas.watchsynopsis.data.local

import androidx.room.Database
    import androidx.room.RoomDatabase

@Database(
    entities = [User::class],
    version = 1, exportSchema = true
)
abstract class MyDatabase : RoomDatabase() {
    abstract val dao: UserDao
}
