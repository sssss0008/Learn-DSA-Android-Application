package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.AppNavTab
import com.example.ui.MainViewModel
import com.example.ui.screens.*
import com.example.ui.theme.*

class MainActivity : ComponentActivity() {
  private val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContent {
      val profile by viewModel.userProfile.collectAsStateWithLifecycle()
      val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
      val currentTheme by viewModel.currentTheme.collectAsStateWithLifecycle()
      val showAiTutor by viewModel.showAiTutor.collectAsStateWithLifecycle()
      val visualizerTarget by viewModel.visualizerTarget.collectAsStateWithLifecycle()

      LearnDataStructuresTheme(themeMode = currentTheme) {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          if (!profile.isOnboarded) {
            OnboardingScreen(
              onComplete = { name, exp, lang, goal, startLvl ->
                viewModel.completeOnboarding(name, exp, lang, goal, startLvl)
              }
            )
          } else {
            // Main Application Navigation Shell
            BackHandler(enabled = currentTab != AppNavTab.HOME) {
              viewModel.setTab(AppNavTab.HOME)
            }

            Scaffold(
              contentWindowInsets = WindowInsets.safeDrawing,
              bottomBar = {
                NavigationBar(
                  containerColor = MaterialTheme.colorScheme.surface,
                  contentColor = MaterialTheme.colorScheme.onSurface,
                  modifier = Modifier.testTag("main_bottom_nav")
                ) {
                  NavigationBarItem(
                    selected = currentTab == AppNavTab.HOME,
                    onClick = { viewModel.setTab(AppNavTab.HOME) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                      selectedIconColor = Color.Black,
                      indicatorColor = NeonCyan
                    )
                  )
                  NavigationBarItem(
                    selected = currentTab == AppNavTab.LEARN,
                    onClick = { viewModel.setTab(AppNavTab.LEARN) },
                    icon = { Icon(Icons.Default.School, contentDescription = "Learn") },
                    label = { Text("Learn", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                      selectedIconColor = Color.Black,
                      indicatorColor = NeonCyan
                    )
                  )
                  NavigationBarItem(
                    selected = currentTab == AppNavTab.VISUALIZE,
                    onClick = { viewModel.setTab(AppNavTab.VISUALIZE) },
                    icon = { Icon(Icons.Default.Visibility, contentDescription = "Visualize") },
                    label = { Text("Visualize", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                      selectedIconColor = Color.Black,
                      indicatorColor = NeonCyan
                    )
                  )
                  NavigationBarItem(
                    selected = currentTab == AppNavTab.PRACTICE,
                    onClick = { viewModel.setTab(AppNavTab.PRACTICE) },
                    icon = { Icon(Icons.Default.FitnessCenter, contentDescription = "Practice") },
                    label = { Text("Practice", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                      selectedIconColor = Color.Black,
                      indicatorColor = NeonCyan
                    )
                  )
                  NavigationBarItem(
                    selected = currentTab == AppNavTab.PROJECTS,
                    onClick = { viewModel.setTab(AppNavTab.PROJECTS) },
                    icon = { Icon(Icons.Default.Terminal, contentDescription = "Projects") },
                    label = { Text("Projects", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                      selectedIconColor = Color.Black,
                      indicatorColor = NeonCyan
                    )
                  )
                  NavigationBarItem(
                    selected = currentTab == AppNavTab.PROFILE,
                    onClick = { viewModel.setTab(AppNavTab.PROFILE) },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                      selectedIconColor = Color.Black,
                      indicatorColor = NeonCyan
                    )
                  )
                }
              }
            ) { innerPadding ->
              Box(
                modifier = Modifier
                  .fillMaxSize()
                  .padding(innerPadding)
              ) {
                when (currentTab) {
                  AppNavTab.HOME -> {
                    HomeScreen(
                      profile = profile,
                      onNavigateToLearn = { viewModel.setTab(AppNavTab.LEARN) },
                      onNavigateToVisualizer = { viewModel.setTab(AppNavTab.VISUALIZE) },
                      onNavigateToPractice = { viewModel.openPractice() },
                      onOpenAiTutor = { viewModel.toggleAiTutor(true) }
                    )
                  }
                  AppNavTab.LEARN -> {
                    LearnScreen(
                      onOpenVisualizer = { target ->
                        viewModel.openVisualizerWithTarget(target)
                      },
                      onOpenPractice = {
                        viewModel.openPractice()
                      },
                      onCompleteLesson = { lessonId ->
                        viewModel.completeLesson(lessonId)
                      }
                    )
                  }
                  AppNavTab.VISUALIZE -> {
                    VisualizerLabScreen(
                      initialType = visualizerTarget
                    )
                  }
                  AppNavTab.PRACTICE -> {
                    PracticeScreen(
                      onPointsEarned = { points ->
                        viewModel.addPoints(points)
                      }
                    )
                  }
                  AppNavTab.PROJECTS -> {
                    ProjectsScreen(
                      onPointsEarned = { points ->
                        viewModel.addPoints(points)
                      }
                    )
                  }
                  AppNavTab.PROFILE -> {
                    ProfileScreen(
                      profile = profile,
                      currentTheme = currentTheme,
                      onThemeChange = { mode -> viewModel.updateTheme(mode) },
                      onResetProgress = { viewModel.resetProgress() }
                    )
                  }
                }

                // AI Tutor Floating Modal
                if (showAiTutor) {
                  AiTutorDialog(
                    onDismiss = { viewModel.toggleAiTutor(false) }
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}
