package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
  @PrimaryKey val id: Int = 1,
  val name: String = "Learner",
  val experience: String = "Beginner",
  val selectedLanguages: String = "Python,Java,C++",
  val learningGoal: String = "Coding Interviews",
  val startLevel: String = "Start From Zero",
  val isOnboarded: Boolean = false,
  val currentStreak: Int = 7,
  val lastActiveDate: Long = System.currentTimeMillis(),
  val themeMode: String = "DARK",
  val currentLevel: Int = 2,
  val dailyGoalMinutes: Int = 20,
  val totalPoints: Int = 450
)

@Entity(tableName = "lesson_progress")
data class LessonProgressEntity(
  @PrimaryKey val lessonId: String,
  val moduleId: Int,
  val isCompleted: Boolean,
  val completedAt: Long = System.currentTimeMillis(),
  val score: Int = 100
)

@Entity(tableName = "quiz_attempts")
data class QuizAttemptEntity(
  @PrimaryKey val quizId: String,
  val score: Int,
  val totalQuestions: Int,
  val passed: Boolean,
  val completedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "challenge_attempts")
data class ChallengeAttemptEntity(
  @PrimaryKey val challengeId: String,
  val solved: Boolean,
  val solvedAt: Long = System.currentTimeMillis(),
  val userCode: String = ""
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
  @PrimaryKey val id: String,
  val itemType: String, // LESSON, VISUALIZER, CHALLENGE, ALGORITHM, PROJECT
  val title: String,
  val subtitle: String,
  val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "notes")
data class NoteEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val topicId: String,
  val title: String,
  val content: String,
  val timestamp: Long = System.currentTimeMillis()
)
