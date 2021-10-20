package com.danielomeara.buildabudget.features.login.data.datasource

import androidx.room.*
import com.danielomeara.buildabudget.features.login.domain.models.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE username=(:username) and password=(:password)")
    suspend fun getUserByUsernamePassword(username: String, password: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Delete()
    suspend fun deleteUser(user: User)
}