package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
  @Query("SELECT * FROM lesson_progress")
  fun getAllLessonProgress(): Flow<List<LessonProgressEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveLessonProgress(progress: LessonProgressEntity)

  @Query("SELECT * FROM quiz_attempts")
  fun getAllQuizAttempts(): Flow<List<QuizAttemptEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveQuizAttempt(attempt: QuizAttemptEntity)

  @Query("SELECT * FROM challenge_attempts")
  fun getAllChallengeAttempts(): Flow<List<ChallengeAttemptEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveChallengeAttempt(attempt: ChallengeAttemptEntity)

  @Query("SELECT * FROM bookmarks ORDER BY timestamp DESC")
  fun getAllBookmarks(): Flow<List<BookmarkEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addBookmark(bookmark: BookmarkEntity)

  @Query("DELETE FROM bookmarks WHERE id = :id")
  suspend fun removeBookmark(id: String)

  @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE id = :id)")
  suspend fun isBookmarked(id: String): Boolean

  @Query("SELECT * FROM notes ORDER BY timestamp DESC")
  fun getAllNotes(): Flow<List<NoteEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveNote(note: NoteEntity)

  @Query("DELETE FROM notes WHERE id = :id")
  suspend fun deleteNote(id: Long)
}
