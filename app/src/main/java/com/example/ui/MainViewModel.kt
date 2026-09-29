package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.LessonProgressEntity
import com.example.data.local.UserProfileEntity
import com.example.ui.theme.AppThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class AppNavTab(val title: String) {
  HOME("Home"),
  LEARN("Learn"),
  VISUALIZE("Visualize"),
  PRACTICE("Practice"),
  PROJECTS("Projects"),
  PROFILE("Profile")
}

class MainViewModel(application: Application) : AndroidViewModel(application) {
  private val db = AppDatabase.getDatabase(application)
  private val userDao = db.userDao()
  private val progressDao = db.progressDao()

  private val _userProfile = MutableStateFlow(
    UserProfileEntity(
      name = "Awiskar",
      experience = "Beginner",
      selectedLanguages = "Python, Java, C++",
      learningGoal = "Coding Interviews",
      startLevel = "Start From Zero",
      isOnboarded = true,
      currentStreak = 7,
      totalPoints = 450,
      currentLevel = 2
    )
  )
  val userProfile: StateFlow<UserProfileEntity> = _userProfile.asStateFlow()

  private val _currentTab = MutableStateFlow(AppNavTab.HOME)
  val currentTab: StateFlow<AppNavTab> = _currentTab.asStateFlow()

  private val _visualizerTarget = MutableStateFlow("ARRAY")
  val visualizerTarget: StateFlow<String> = _visualizerTarget.asStateFlow()

  private val _currentTheme = MutableStateFlow(AppThemeMode.DARK)
  val currentTheme: StateFlow<AppThemeMode> = _currentTheme.asStateFlow()

  private val _showAiTutor = MutableStateFlow(false)
  val showAiTutor: StateFlow<Boolean> = _showAiTutor.asStateFlow()

  init {
    viewModelScope.launch {
      userDao.getUserProfile().collect { profile ->
        if (profile != null) {
          _userProfile.value = profile
          try {
            _currentTheme.value = AppThemeMode.valueOf(profile.themeMode)
          } catch (e: Exception) {
            _currentTheme.value = AppThemeMode.DARK
          }
        } else {
          // Initialize default profile
          val defaultProf = UserProfileEntity()
          userDao.saveUserProfile(defaultProf)
          _userProfile.value = defaultProf
        }
      }
    }
  }

  fun setTab(tab: AppNavTab) {
    _currentTab.value = tab
  }

  fun openVisualizerWithTarget(target: String) {
    _visualizerTarget.value = target
    _currentTab.value = AppNavTab.VISUALIZE
  }

  fun openPractice() {
    _currentTab.value = AppNavTab.PRACTICE
  }

  fun toggleAiTutor(show: Boolean) {
    _showAiTutor.value = show
  }

  fun completeOnboarding(name: String, experience: String, language: String, goal: String, startLevel: String) {
    viewModelScope.launch {
      val updated = _userProfile.value.copy(
        name = name,
        experience = experience,
        selectedLanguages = language,
        learningGoal = goal,
        startLevel = startLevel,
        isOnboarded = true,
        currentStreak = 1
      )
      userDao.saveUserProfile(updated)
      _userProfile.value = updated
    }
  }

  fun completeLesson(lessonId: String) {
    viewModelScope.launch {
      progressDao.saveLessonProgress(
        LessonProgressEntity(
          lessonId = lessonId,
          moduleId = 2,
          isCompleted = true,
          score = 100
        )
      )
      userDao.addPoints(50)
      _userProfile.value = _userProfile.value.copy(
        totalPoints = _userProfile.value.totalPoints + 50
      )
    }
  }

  fun addPoints(points: Int) {
    viewModelScope.launch {
      userDao.addPoints(points)
      _userProfile.value = _userProfile.value.copy(
        totalPoints = _userProfile.value.totalPoints + points
      )
    }
  }

  fun updateTheme(themeMode: AppThemeMode) {
    _currentTheme.value = themeMode
    viewModelScope.launch {
      userDao.updateTheme(themeMode.name)
    }
  }

  fun resetProgress() {
    viewModelScope.launch {
      val reset = _userProfile.value.copy(
        totalPoints = 0,
        currentStreak = 1,
        currentLevel = 1
      )
      userDao.saveUserProfile(reset)
      _userProfile.value = reset
    }
  }
}
