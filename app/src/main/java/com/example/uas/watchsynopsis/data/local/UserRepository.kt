package com.example.uas.watchsynopsis.data.local

interface Repository {

    suspend fun insert(user: User)

    suspend fun delete(user: User)

    suspend fun update(user: User)

    suspend fun getUserByEmailAndPassword(email: String, password: String): User?

    suspend fun getUserNameByEmail(email: String): String?
}

