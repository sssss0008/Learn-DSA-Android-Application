package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun OnboardingScreen(
  onComplete: (name: String, experience: String, language: String, goal: String, startLevel: String) -> Unit,
  modifier: Modifier = Modifier
) {
  var currentStep by remember { mutableStateOf(0) }
  var name by remember { mutableStateOf("Awiskar") }
  var experience by remember { mutableStateOf("Beginner") }
  var selectedLanguage by remember { mutableStateOf("Python") }
  var learningGoal by remember { mutableStateOf("Coding interviews") }
  var startLevel by remember { mutableStateOf("Start From Zero") }

  val experienceOptions = listOf("Complete Beginner", "Beginner", "Intermediate", "Advanced")
  val languageOptions = listOf("Python", "Java", "C++", "C", "JavaScript", "Kotlin", "C#", "Go", "Rust")
  val goalOptions = listOf(
    "Coding interviews",
    "College studies",
    "Learn programming fundamentals",
    "Competitive programming",
    "Improve problem solving",
    "Software development",
    "Prepare for exams",
    "Build projects"
  )
  val startLevelOptions = listOf("Start From Zero", "I Know The Basics", "Jump To Practice")

  Scaffold(
    modifier = modifier.fillMaxSize(),
    containerColor = MaterialTheme.colorScheme.background
  ) { padding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding)
        .padding(horizontal = 24.dp, vertical = 16.dp)
        .verticalScroll(rememberScrollState()),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Top header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(Brush.linearGradient(listOf(MidnightBlue, ElectricBlue)))
          ) {
            Icon(
              imageVector = Icons.Default.DeviceHub,
              contentDescription = null,
              tint = NeonCyan,
              modifier = Modifier.size(20.dp)
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "Learn Data Structures",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
        }

        Surface(
          shape = RoundedCornerShape(12.dp),
          color = AccentGreen.copy(alpha = 0.15f),
          border = androidx.compose.foundation.BorderStroke(1.dp, AccentGreen.copy(alpha = 0.5f))
        ) {
          Text(
            text = "100% FREE",
            style = ComplexityBadgeStyle.copy(color = AccentGreen, fontSize = 10.sp),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Progress bar (5 steps)
      LinearProgressIndicator(
        progress = { (currentStep + 1) / 6f },
        modifier = Modifier
          .fillMaxWidth()
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp)),
        color = NeonCyan,
        trackColor = MaterialTheme.colorScheme.surfaceVariant
      )

      Spacer(modifier = Modifier.height(24.dp))

      // Content for steps
      when (currentStep) {
        0 -> {
          // Welcome & Intro
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(100.dp)
              .clip(CircleShape)
              .background(Brush.radialGradient(listOf(MidnightBlue, DeepNavy)))
              .border(2.dp, NeonCyan, CircleShape)
          ) {
            Icon(
              imageVector = Icons.Default.AccountTree,
              contentDescription = null,
              tint = NeonCyan,
              modifier = Modifier.size(54.dp)
            )
          }

          Spacer(modifier = Modifier.height(20.dp))

          Text(
            text = "Master Data Structures Visually",
            style = MaterialTheme.typography.headlineLarge.copy(
              fontWeight = FontWeight.Bold,
              textAlign = TextAlign.Center
            )
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "“Understand. Visualize. Code. Master Data Structures.”\nCreated by Awiskar Acharya",
            style = MaterialTheme.typography.bodyMedium.copy(
              color = ElectricBlue,
              textAlign = TextAlign.Center
            )
          )

          Spacer(modifier = Modifier.height(16.dp))

          Text(
            text = "Learn how data is organized, stored, accessed, and manipulated through interactive lessons, real-time visualizers, and coding practice.",
            style = MaterialTheme.typography.bodyMedium.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
          )

          Spacer(modifier = Modifier.height(32.dp))

          Button(
            onClick = { currentStep++ },
            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = Color.Black),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(52.dp)
              .testTag("onboarding_start_button")
          ) {
            Text(text = "Start Learning", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.ArrowForward, contentDescription = null)
          }
        }

        1 -> {
          // Step 1: Name
          Text(
            text = "Step 1 — What should we call you?",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "Personalize your learning certificates and dashboard.",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )

          Spacer(modifier = Modifier.height(24.dp))

          OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter your name") },
            placeholder = { Text("e.g. Awiskar") },
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_learner_name"),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = NeonCyan,
              unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
          )

          Spacer(modifier = Modifier.height(32.dp))

          Button(
            onClick = { if (name.isNotBlank()) currentStep++ },
            enabled = name.isNotBlank(),
            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = Color.Black),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)
              .testTag("onboarding_next_step1")
          ) {
            Text("Next: Programming Experience", fontWeight = FontWeight.SemiBold)
          }
        }

        2 -> {
          // Step 2: Experience
          Text(
            text = "Step 2 — Programming Experience",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "We will adapt lesson explanations and visual animations accordingly.",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )

          Spacer(modifier = Modifier.height(20.dp))

          experienceOptions.forEach { opt ->
            val isSelected = experience == opt
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(
                containerColor = if (isSelected) MidnightBlue else MaterialTheme.colorScheme.surfaceVariant
              ),
              border = androidx.compose.foundation.BorderStroke(
                1.5.dp,
                if (isSelected) NeonCyan else MaterialTheme.colorScheme.outline
              ),
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
                .clickable { experience = opt }
                .testTag("exp_option_$opt")
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = opt,
                  style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) NeonCyan else MaterialTheme.colorScheme.onSurface
                  )
                )
                if (isSelected) {
                  Icon(Icons.Default.CheckCircle, contentDescription = null, tint = NeonCyan)
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(24.dp))

          Button(
            onClick = { currentStep++ },
            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = Color.Black),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)
              .testTag("onboarding_next_step2")
          ) {
            Text("Next: Programming Language", fontWeight = FontWeight.SemiBold)
          }
        }

        3 -> {
          // Step 3: Programming Language
          Text(
            text = "Step 3 — Preferred Code Language",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "Code examples in every module will be rendered in this language.",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )

          Spacer(modifier = Modifier.height(16.dp))

          Column(modifier = Modifier.fillMaxWidth()) {
            languageOptions.chunked(3).forEach { rowLangs ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                rowLangs.forEach { lang ->
                  val isSelected = selectedLanguage == lang
                  Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                      .weight(1f)
                      .clip(RoundedCornerShape(10.dp))
                      .background(if (isSelected) MidnightBlue else MaterialTheme.colorScheme.surfaceVariant)
                      .border(
                        1.5.dp,
                        if (isSelected) NeonCyan else MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(10.dp)
                      )
                      .clickable { selectedLanguage = lang }
                      .padding(vertical = 12.dp)
                      .testTag("lang_option_$lang")
                  ) {
                    Text(
                      text = lang,
                      style = CodeTextStyle.copy(
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) NeonCyan else MaterialTheme.colorScheme.onSurface
                      )
                    )
                  }
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(24.dp))

          Button(
            onClick = { currentStep++ },
            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = Color.Black),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)
              .testTag("onboarding_next_step3")
          ) {
            Text("Next: Learning Goal", fontWeight = FontWeight.SemiBold)
          }
        }

        4 -> {
          // Step 4: Learning Goal & Step 5: Start Level
          Text(
            text = "Step 4 & 5 — Goal & Starting Level",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
          )

          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = "Select primary goal:",
            style = MaterialTheme.typography.labelLarge.copy(color = ElectricBlue)
          )

          goalOptions.take(4).forEach { goal ->
            val isSelected = learningGoal == goal
            Card(
              shape = RoundedCornerShape(10.dp),
              colors = CardDefaults.cardColors(
                containerColor = if (isSelected) MidnightBlue else MaterialTheme.colorScheme.surfaceVariant
              ),
              border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (isSelected) NeonCyan else MaterialTheme.colorScheme.outline
              ),
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable { learningGoal = goal }
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(text = goal, style = MaterialTheme.typography.bodyMedium)
                if (isSelected) Icon(Icons.Default.Check, contentDescription = null, tint = NeonCyan)
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))
          Text(
            text = "Select start level:",
            style = MaterialTheme.typography.labelLarge.copy(color = ElectricBlue)
          )

          startLevelOptions.forEach { lvl ->
            val isSelected = startLevel == lvl
            Card(
              shape = RoundedCornerShape(10.dp),
              colors = CardDefaults.cardColors(
                containerColor = if (isSelected) MidnightBlue else MaterialTheme.colorScheme.surfaceVariant
              ),
              border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (isSelected) NeonCyan else MaterialTheme.colorScheme.outline
              ),
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable { startLevel = lvl }
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(text = lvl, style = MaterialTheme.typography.bodyMedium)
                if (isSelected) Icon(Icons.Default.Check, contentDescription = null, tint = NeonCyan)
              }
            }
          }

          Spacer(modifier = Modifier.height(24.dp))

          Button(
            onClick = { currentStep++ },
            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = Color.Black),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)
              .testTag("onboarding_next_step4")
          ) {
            Text("Preview Personalized Roadmap", fontWeight = FontWeight.SemiBold)
          }
        }

        5 -> {
          // Personalized Start summary
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan.copy(alpha = 0.6f)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(20.dp)) {
              Text(
                text = "Welcome, $name!",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, color = NeonCyan)
              )
              Text(
                text = "Let's build your Data Structures journey.",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
              )

              Spacer(modifier = Modifier.height(16.dp))
              HorizontalDivider(color = MaterialTheme.colorScheme.outline)
              Spacer(modifier = Modifier.height(16.dp))

              Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Starting Level:", style = MaterialTheme.typography.bodySmall)
                Text(startLevel, style = CodeTextStyle.copy(color = ElectricBlue))
              }
              Spacer(modifier = Modifier.height(6.dp))

              Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Preferred Language:", style = MaterialTheme.typography.bodySmall)
                Text(selectedLanguage, style = CodeTextStyle.copy(color = NeonCyan))
              }
              Spacer(modifier = Modifier.height(6.dp))

              Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Goal:", style = MaterialTheme.typography.bodySmall)
                Text(learningGoal, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
              }
              Spacer(modifier = Modifier.height(6.dp))

              Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Estimated Lessons:", style = MaterialTheme.typography.bodySmall)
                Text("85+ Interactive Labs", style = CodeTextStyle)
              }
            }
          }

          Spacer(modifier = Modifier.height(28.dp))

          Button(
            onClick = {
              onComplete(name, experience, selectedLanguage, learningGoal, startLevel)
            },
            colors = ButtonDefaults.buttonColors(
              containerColor = NeonCyan,
              contentColor = Color.Black
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(52.dp)
              .testTag("onboarding_complete_button")
          ) {
            Text(text = "Start My Journey", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.RocketLaunch, contentDescription = null)
          }
        }
      }
    }
  }
}
