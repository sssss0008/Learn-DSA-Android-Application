package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
  @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
  fun getUserProfile(): Flow<UserProfileEntity?>

  @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
  suspend fun getUserProfileOnce(): UserProfileEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveUserProfile(profile: UserProfileEntity)

  @Query("UPDATE user_profile SET themeMode = :themeMode WHERE id = 1")
  suspend fun updateTheme(themeMode: String)

  @Query("UPDATE user_profile SET currentStreak = currentStreak + 1 WHERE id = 1")
  suspend fun incrementStreak()

  @Query("UPDATE user_profile SET totalPoints = totalPoints + :points WHERE id = 1")
  suspend fun addPoints(points: Int)
}
