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
import com.example.data.model.*
import com.example.data.repository.DsRepository
import com.example.ui.components.CodeBlockView
import com.example.ui.components.ComplexityBadge
import com.example.ui.theme.*

@Composable
fun PracticeScreen(
  onPointsEarned: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf(0) }
  val tabTitles = listOf("Challenges", "Debug Lab", "DS Selector", "Module Quiz", "Flashcards")

  var activeChallenge by remember { mutableStateOf<CodingChallenge?>(null) }
  var testResultText by remember { mutableStateOf<String?>(null) }

  // Debug lab state
  var isBugFixed by remember { mutableStateOf(false) }

  // DS Selector State
  var selectedNeed by remember { mutableStateOf<String?>(null) }

  // Quiz state
  var currentQuizQuestionIdx by remember { mutableStateOf(0) }
  var selectedOptionIdx by remember { mutableStateOf<Int?>(null) }
  var quizScore by remember { mutableStateOf(0) }
  var quizSubmitted by remember { mutableStateOf(false) }

  // Flashcards state
  var currentCardIdx by remember { mutableStateOf(0) }
  var isCardFlipped by remember { mutableStateOf(false) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 14.dp, vertical = 8.dp)
      .testTag("practice_screen")
  ) {
    // Top Tabs
    ScrollableTabRow(
      selectedTabIndex = selectedTab,
      edgePadding = 4.dp,
      containerColor = MaterialTheme.colorScheme.background,
      contentColor = ElectricBlue,
      modifier = Modifier.fillMaxWidth()
    ) {
      tabTitles.forEachIndexed { index, title ->
        Tab(
          selected = selectedTab == index,
          onClick = {
            selectedTab = index
            activeChallenge = null
            testResultText = null
          },
          text = {
            Text(
              text = title,
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                color = if (selectedTab == index) NeonCyan else MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    when (selectedTab) {
      0 -> {
        // Challenges list & active challenge
        if (activeChallenge != null) {
          val ch = activeChallenge!!
          Column(
            modifier = Modifier
              .fillMaxSize()
              .verticalScroll(rememberScrollState())
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              IconButton(onClick = { activeChallenge = null }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
              }
              ComplexityBadge(label = "Difficulty", complexity = ch.difficulty.label, color = WarningAmber)
              ComplexityBadge(label = "Points", complexity = "+${ch.points}", color = AccentGreen)
            }

            Text(
              text = ch.title,
              style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = ch.problemStatement, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "Example:",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ElectricBlue)
            )
            ch.examples.forEach { (inp, out) ->
              Text(text = "$inp -> $out", style = CodeTextStyle.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
            }

            Spacer(modifier = Modifier.height(14.dp))
            Text(
              text = "Code Implementation:",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ElectricBlue)
            )
            val code = ch.initialCode[ProgrammingLanguage.PYTHON] ?: "# Write code"
            CodeBlockView(code = code, language = "Python")

            Spacer(modifier = Modifier.height(14.dp))

            Button(
              onClick = {
                testResultText = "✓ All 3 Test Cases Passed! Complexity O(n) verified."
                onPointsEarned(ch.points)
              },
              colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Icon(Icons.Default.PlayArrow, contentDescription = null)
              Spacer(modifier = Modifier.width(6.dp))
              Text("Run Tests & Submit Solution", fontWeight = FontWeight.Bold)
            }

            if (testResultText != null) {
              Spacer(modifier = Modifier.height(10.dp))
              Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = AccentGreen.copy(alpha = 0.15f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, AccentGreen)
              ) {
                Text(
                  text = testResultText!!,
                  style = CodeTextStyle.copy(color = AccentGreen, fontWeight = FontWeight.Bold),
                  modifier = Modifier.padding(12.dp)
                )
              }
            }
          }
        } else {
          // List of challenges
          LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(DsRepository.sampleChallenges) { ch ->
              Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                modifier = Modifier
                  .fillMaxWidth()
                  .clickable { activeChallenge = ch }
              ) {
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Column(modifier = Modifier.weight(1f)) {
                    Text(text = ch.title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Text(
                      text = "${ch.topic} • ~${ch.estimatedMinutes} mins",
                      style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                  }
                  ComplexityBadge(label = "Pts", complexity = "+${ch.points}", color = AccentGreen)
                }
              }
            }
          }
        }
      }

      1 -> {
        // Debugging Lab
        Column(
          modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ) {
          Text(
            text = "Debugging Lab: Fix Pointer Error",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "Analyze this broken Linked List node insertion and find the bug.",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )

          Spacer(modifier = Modifier.height(12.dp))

          Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            border = androidx.compose.foundation.BorderStroke(1.dp, if (isBugFixed) AccentGreen else ErrorCrimson)
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Text(
                text = if (isBugFixed) "CORRECTED CODE:" else "BUGGY CODE (Lost Reference):",
                style = ComplexityBadgeStyle.copy(color = if (isBugFixed) AccentGreen else ErrorCrimson)
              )
              Spacer(modifier = Modifier.height(6.dp))

              val bugCode = if (isBugFixed) {
                """
                # Fixed: Correct pointer order
                new_node.next = prev_node.next
                prev_node.next = new_node
                """.trimIndent()
              } else {
                """
                # BUG: Reassigning prev_node.next first breaks the link!
                prev_node.next = new_node
                new_node.next = prev_node.next  # Infinite loop!
                """.trimIndent()
              }

              Text(text = bugCode, style = CodeTextStyle.copy(color = MaterialTheme.colorScheme.onSurface))
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          if (!isBugFixed) {
            Button(
              onClick = {
                isBugFixed = true
                onPointsEarned(35)
              },
              colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Icon(Icons.Default.Build, contentDescription = null)
              Spacer(modifier = Modifier.width(6.dp))
              Text("Fix Pointer Reassignment Bug (+35 pts)", fontWeight = FontWeight.Bold)
            }
          } else {
            Card(
              shape = RoundedCornerShape(10.dp),
              colors = CardDefaults.cardColors(containerColor = AccentGreen.copy(alpha = 0.15f)),
              border = androidx.compose.foundation.BorderStroke(1.dp, AccentGreen)
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Text("✓ Bug Fixed Successfully!", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = AccentGreen))
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  "By assigning new_node.next = prev_node.next first, you safely preserve the reference to subsequent nodes before updating prev_node.next.",
                  style = MaterialTheme.typography.bodySmall
                )
              }
            }
          }
        }
      }

      2 -> {
        // DS Selector Tool (Decision Engine)
        Column(
          modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ) {
          Text(
            text = "Data Structure Selector",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "What is the primary access or mutation requirement of your program?",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )

          Spacer(modifier = Modifier.height(12.dp))

          val options = listOf(
            "Instant O(1) Key-Value Lookup" to ("Hash Table" to "Uses hashing to compute direct bucket index; O(1) expected time."),
            "Strict Last-In-First-Out (LIFO)" to ("Stack" to "Enforces push/pop operations at top; ideal for undo, parenthesis matching, recursion."),
            "Strict First-In-First-Out (FIFO)" to ("Queue" to "Enqueue at rear, dequeue at front; ideal for BFS and message dispatchers."),
            "Hierarchical / Sorted Tree Query" to ("Binary Search Tree (BST)" to "Left < Root < Right maintains ordering with O(log n) operations."),
            "Fast Min/Max Element Retrieval" to ("Binary Heap / Priority Queue" to "O(1) peek at extreme element; O(log n) extract; optimal for Dijkstra."),
            "Prefix Search / Autocomplete" to ("Trie" to "Character-level tree branches; O(k) lookup where k is query string length.")
          )

          options.forEach { (need, recommendation) ->
            val isSelected = selectedNeed == need
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
                .clickable { selectedNeed = need }
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Text(text = need, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                if (isSelected) {
                  Spacer(modifier = Modifier.height(6.dp))
                  Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = AccentGreen.copy(alpha = 0.2f),
                    border = androidx.compose.foundation.BorderStroke(0.8.dp, AccentGreen)
                  ) {
                    Text(
                      text = "Recommended: ${recommendation.first}",
                      style = CodeTextStyle.copy(fontWeight = FontWeight.Bold, color = AccentGreen),
                      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                  }
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(text = recommendation.second, style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                }
              }
            }
          }
        }
      }

      3 -> {
        // Module Quiz Engine
        val q = DsRepository.sampleQuiz[currentQuizQuestionIdx]
        Column(
          modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Question ${currentQuizQuestionIdx + 1} of ${DsRepository.sampleQuiz.size}",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = "Score: $quizScore",
              style = CodeTextStyle.copy(color = NeonCyan, fontWeight = FontWeight.Bold)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))
          Text(text = q.question, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold))
          Spacer(modifier = Modifier.height(14.dp))

          q.options.forEachIndexed { optIdx, optText ->
            val isSelected = selectedOptionIdx == optIdx
            val isCorrect = optIdx == q.correctIndex
            val cardBg = when {
              quizSubmitted && isCorrect -> AccentGreen.copy(alpha = 0.2f)
              quizSubmitted && isSelected && !isCorrect -> ErrorCrimson.copy(alpha = 0.2f)
              isSelected -> MidnightBlue
              else -> MaterialTheme.colorScheme.surfaceVariant
            }

            Card(
              shape = RoundedCornerShape(10.dp),
              colors = CardDefaults.cardColors(containerColor = cardBg),
              border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (isSelected) NeonCyan else MaterialTheme.colorScheme.outline
              ),
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable(enabled = !quizSubmitted) {
                  selectedOptionIdx = optIdx
                }
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "${('A' + optIdx)}. $optText",
                  style = MaterialTheme.typography.bodyMedium
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          if (!quizSubmitted) {
            Button(
              onClick = {
                if (selectedOptionIdx != null) {
                  quizSubmitted = true
                  if (selectedOptionIdx == q.correctIndex) {
                    quizScore += 20
                    onPointsEarned(20)
                  }
                }
              },
              enabled = selectedOptionIdx != null,
              colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = Color.Black),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text("Submit Answer", fontWeight = FontWeight.Bold)
            }
          } else {
            Card(
              shape = RoundedCornerShape(10.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
              border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan.copy(alpha = 0.4f))
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Text("Explanation:", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = NeonCyan))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = q.explanation, style = MaterialTheme.typography.bodySmall)
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
              onClick = {
                if (currentQuizQuestionIdx < DsRepository.sampleQuiz.size - 1) {
                  currentQuizQuestionIdx++
                  selectedOptionIdx = null
                  quizSubmitted = false
                } else {
                  currentQuizQuestionIdx = 0
                  selectedOptionIdx = null
                  quizSubmitted = false
                }
              },
              colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = if (currentQuizQuestionIdx < DsRepository.sampleQuiz.size - 1) "Next Question" else "Restart Quiz",
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }

      4 -> {
        // Flashcards
        val fc = DsRepository.flashcards[currentCardIdx]
        Column(
          modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Flashcard ${currentCardIdx + 1} of ${DsRepository.flashcards.size}",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = MidnightBlue
            ) {
              Text(
                text = fc.category,
                style = ComplexityBadgeStyle.copy(color = NeonCyan, fontSize = 10.sp),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(24.dp))

          // Flippable Card
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
              containerColor = if (isCardFlipped) MidnightBlue else MaterialTheme.colorScheme.surfaceVariant
            ),
            border = androidx.compose.foundation.BorderStroke(
              1.5.dp,
              if (isCardFlipped) AccentGreen else ElectricBlue
            ),
            modifier = Modifier
              .fillMaxWidth()
              .height(200.dp)
              .clickable { isCardFlipped = !isCardFlipped }
          ) {
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                  text = if (isCardFlipped) "ANSWER:" else "QUESTION (Tap to flip):",
                  style = ComplexityBadgeStyle.copy(
                    color = if (isCardFlipped) AccentGreen else ElectricBlue,
                    fontSize = 11.sp
                  )
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                  text = if (isCardFlipped) fc.back else fc.front,
                  style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                  )
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(24.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
          ) {
            OutlinedButton(
              onClick = {
                if (currentCardIdx > 0) {
                  currentCardIdx--
                  isCardFlipped = false
                }
              },
              enabled = currentCardIdx > 0,
              shape = RoundedCornerShape(8.dp)
            ) {
              Icon(Icons.Default.ArrowBack, contentDescription = null)
              Spacer(modifier = Modifier.width(4.dp))
              Text("Previous")
            }

            Button(
              onClick = {
                if (currentCardIdx < DsRepository.flashcards.size - 1) {
                  currentCardIdx++
                  isCardFlipped = false
                } else {
                  currentCardIdx = 0
                  isCardFlipped = false
                }
              },
              colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = Color.Black),
              shape = RoundedCornerShape(8.dp)
            ) {
              Text("Next Card")
              Spacer(modifier = Modifier.width(4.dp))
              Icon(Icons.Default.ArrowForward, contentDescription = null)
            }
          }
        }
      }
    }
  }
}
