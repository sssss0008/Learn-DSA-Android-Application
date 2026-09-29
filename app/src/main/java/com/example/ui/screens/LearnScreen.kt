package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CurriculumModule
import com.example.data.model.Lesson
import com.example.data.model.ProgrammingLanguage
import com.example.data.repository.DsRepository
import com.example.ui.components.CodeBlockView
import com.example.ui.components.ComplexityBadge
import com.example.ui.theme.*

@Composable
fun LearnScreen(
  onOpenVisualizer: (String) -> Unit,
  onOpenPractice: (String) -> Unit,
  onCompleteLesson: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedModule by remember { mutableStateOf<CurriculumModule?>(null) }
  var selectedLesson by remember { mutableStateOf<Lesson?>(null) }
  var codeLanguage by remember { mutableStateOf(ProgrammingLanguage.PYTHON) }

  if (selectedLesson != null) {
    LessonDetailModal(
      lesson = selectedLesson!!,
      language = codeLanguage,
      onLanguageChange = { codeLanguage = it },
      onClose = { selectedLesson = null },
      onOpenVisualizer = {
        val target = when (selectedLesson!!.moduleId) {
          2 -> "ARRAY"
          4 -> "LINKED_LIST"
          5 -> "STACK"
          6 -> "QUEUE"
          7 -> "HASH_TABLE"
          9, 10 -> "BST"
          14 -> "GRAPH"
          else -> "ARRAY"
        }
        onOpenVisualizer(target)
      },
      onMarkComplete = {
        onCompleteLesson(selectedLesson!!.id)
        selectedLesson = null
      }
    )
    return
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp)
      .testTag("learn_screen")
  ) {
    // Header
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Column {
        Text(
          text = "Learning Roadmap",
          style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
          text = "20 Levels — Beginner to Interview Mastery",
          style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
      }

      Surface(
        shape = RoundedCornerShape(12.dp),
        color = NeonCyan.copy(alpha = 0.15f),
        border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan.copy(alpha = 0.4f))
      ) {
        Text(
          text = "FREE CURRICULUM",
          style = ComplexityBadgeStyle.copy(color = NeonCyan, fontSize = 10.sp),
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
      }
    }

    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(10.dp),
      contentPadding = PaddingValues(bottom = 24.dp)
    ) {
      items(DsRepository.modules) { module ->
        val isExpanded = selectedModule?.id == module.id
        ModuleCard(
          module = module,
          isExpanded = isExpanded,
          onCardClick = {
            selectedModule = if (isExpanded) null else module
          },
          onLessonClick = { lesson ->
            selectedLesson = lesson
          }
        )
      }
    }
  }
}

@Composable
fun ModuleCard(
  module: CurriculumModule,
  isExpanded: Boolean,
  onCardClick: () -> Unit,
  onLessonClick: (Lesson) -> Unit
) {
  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isExpanded) MidnightBlue.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surfaceVariant
    ),
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      if (isExpanded) ElectricBlue else MaterialTheme.colorScheme.outline
    ),
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onCardClick() }
      .testTag("module_card_${module.id}")
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(if (module.isAdvanced) ElectricViolet else MidnightBlue)
          ) {
            Text(
              text = "L${module.level}",
              style = CodeTextStyle.copy(fontWeight = FontWeight.Bold, color = NeonCyan, fontSize = 12.sp)
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = module.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
              )
              if (module.isAdvanced) {
                Spacer(modifier = Modifier.width(6.dp))
                Surface(
                  shape = RoundedCornerShape(4.dp),
                  color = TechPurple.copy(alpha = 0.2f)
                ) {
                  Text(
                    text = "ADVANCED",
                    style = ComplexityBadgeStyle.copy(color = TechPurple, fontSize = 9.sp),
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                  )
                }
              }
            }
            Text(
              text = "${module.lessonsCount} Lessons • ~${module.estimatedMinutes} mins",
              style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
          }
        }

        Icon(
          imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }

      if (isExpanded) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
          text = module.description,
          style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
        Spacer(modifier = Modifier.height(10.dp))

        if (module.lessons.isNotEmpty()) {
          module.lessons.forEach { lesson ->
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = MaterialTheme.colorScheme.surface,
              border = androidx.compose.foundation.BorderStroke(0.8.dp, MaterialTheme.colorScheme.outline),
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable { onLessonClick(lesson) }
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = lesson.title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                  )
                  Text(
                    text = lesson.summary,
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                    maxLines = 1
                  )
                }
                Icon(Icons.Default.ArrowForward, contentDescription = "View lesson", tint = ElectricBlue)
              }
            }
          }
        } else {
          // Preview placeholder for subsequent modules
          Text(
            text = "Detailed lessons, animations, and code challenges available in Visual Lab & Practice.",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )
        }
      }
    }
  }
}

@Composable
fun LessonDetailModal(
  lesson: Lesson,
  language: ProgrammingLanguage,
  onLanguageChange: (ProgrammingLanguage) -> Unit,
  onClose: () -> Unit,
  onOpenVisualizer: () -> Unit,
  onMarkComplete: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(16.dp)
      .verticalScroll(rememberScrollState())
      .testTag("lesson_reader_${lesson.id}")
  ) {
    // Top Bar
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onClose) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
      }
      Text(
        text = "Lesson Reader",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
      Button(
        onClick = onMarkComplete,
        colors = ButtonDefaults.buttonColors(containerColor = AccentGreen, contentColor = Color.Black),
        shape = RoundedCornerShape(8.dp)
      ) {
        Text("Done", fontWeight = FontWeight.Bold)
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    Text(
      text = lesson.title,
      style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
    )

    Spacer(modifier = Modifier.height(8.dp))

    // Complexity badges
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      ComplexityBadge(label = "Time", complexity = lesson.timeComplexity, color = NeonCyan)
      ComplexityBadge(label = "Space", complexity = lesson.spaceComplexity, color = ElectricBlue)
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Concept Section
    Text(
      text = "Concept Explanation",
      style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = ElectricBlue)
    )
    Spacer(modifier = Modifier.height(6.dp))
    Text(
      text = lesson.concept,
      style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp)
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Visual Diagram Description
    Card(
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
      border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan.copy(alpha = 0.4f)),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Visual Representation",
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold, color = NeonCyan)
          )
          OutlinedButton(
            onClick = onOpenVisualizer,
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
          ) {
            Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Open Simulator")
          }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = lesson.visualDescription,
          style = CodeTextStyle.copy(color = MaterialTheme.colorScheme.onSurface)
        )
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Code Implementation with Language switcher
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "Code Implementation",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = ElectricBlue)
      )

      // Language Chips
      Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        listOf(ProgrammingLanguage.PYTHON, ProgrammingLanguage.JAVA, ProgrammingLanguage.CPP).forEach { lang ->
          val isSelected = language == lang
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = if (isSelected) MidnightBlue else MaterialTheme.colorScheme.surfaceVariant,
            border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) NeonCyan else Color.Transparent),
            modifier = Modifier.clickable { onLanguageChange(lang) }
          ) {
            Text(
              text = lang.displayName,
              style = CodeTextStyle.copy(
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) NeonCyan else MaterialTheme.colorScheme.onSurfaceVariant
              ),
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    val currentCode = lesson.codeSnippets[language] ?: lesson.codeSnippets[ProgrammingLanguage.PYTHON] ?: "// Code sample"
    CodeBlockView(code = currentCode, language = language.displayName)

    Spacer(modifier = Modifier.height(18.dp))

    // Real world applications
    Text(
      text = "Real-World Applications",
      style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = ElectricBlue)
    )
    Spacer(modifier = Modifier.height(6.dp))
    lesson.realWorldApplications.forEach { app ->
      Row(modifier = Modifier.padding(vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Check, contentDescription = null, tint = AccentGreen, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = app, style = MaterialTheme.typography.bodyMedium)
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Common Mistakes
    Text(
      text = "Common Mistakes to Avoid",
      style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = WarningAmber)
    )
    Spacer(modifier = Modifier.height(6.dp))
    lesson.commonMistakes.forEach { mistake ->
      Row(modifier = Modifier.padding(vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Warning, contentDescription = null, tint = WarningAmber, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = mistake, style = MaterialTheme.typography.bodyMedium)
      }
    }

    Spacer(modifier = Modifier.height(20.dp))
  }
}
