package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ComplexityBadge
import com.example.ui.theme.*

data class TutorMessage(
  val sender: String, // USER or TUTOR
  val text: String,
  val visualDiagram: String = "",
  val complexity: String = ""
)

@Composable
fun AiTutorDialog(
  onDismiss: () -> Unit,
  modifier: Modifier = Modifier
) {
  var userInput by remember { mutableStateOf("") }
  var messages by remember {
    mutableStateOf(
      listOf(
        TutorMessage(
          sender = "TUTOR",
          text = "Hello! I am your AI Data Structures Tutor. Ask me any conceptual question, ask for visual explanations, or choose a prompt below!",
          visualDiagram = "Concept -> Visualization -> Code -> Practice",
          complexity = "All questions 100% Free"
        )
      )
    )
  }

  val suggestedPrompts = listOf(
    "Explain linked lists like I'm a beginner",
    "Why is binary search O(log n)?",
    "Show me how BFS works",
    "Why would I use a hash table?",
    "Help me understand recursion"
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(14.dp)
      .testTag("ai_tutor_dialog")
  ) {
    // Top bar
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
            .background(MidnightBlue)
        ) {
          Icon(Icons.Default.Psychology, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(text = "AI Data Structure Tutor", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
          Text(text = "Visual intuition & complexity breakdown", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
        }
      }

      IconButton(onClick = onDismiss) {
        Icon(Icons.Default.Close, contentDescription = "Close")
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Suggested Prompts
    Text(text = "Quick Inquiries:", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
    Spacer(modifier = Modifier.height(4.dp))
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp),
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      suggestedPrompts.take(2).forEach { prompt ->
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = MaterialTheme.colorScheme.surfaceVariant,
          border = androidx.compose.foundation.BorderStroke(0.8.dp, ElectricBlue.copy(alpha = 0.4f)),
          modifier = Modifier
            .weight(1f)
            .clickable {
              processUserQuestion(prompt) { answer, diag, comp ->
                messages = messages + TutorMessage("USER", prompt) + TutorMessage("TUTOR", answer, diag, comp)
              }
            }
        ) {
          Text(
            text = prompt,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
            maxLines = 1,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
          )
        }
      }
    }

    // Message History
    Column(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      messages.forEach { msg ->
        val isUser = msg.sender == "USER"
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
        ) {
          Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
              containerColor = if (isUser) MidnightBlue else MaterialTheme.colorScheme.surfaceVariant
            ),
            border = androidx.compose.foundation.BorderStroke(
              1.dp,
              if (isUser) ElectricBlue else MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier.widthIn(max = 320.dp)
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Text(
                text = if (isUser) "You" else "AI Tutor (Awiskar Academy)",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = if (isUser) ElectricBlue else NeonCyan
                )
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(text = msg.text, style = MaterialTheme.typography.bodyMedium)

              if (msg.visualDiagram.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = MaterialTheme.colorScheme.surface,
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Text(
                    text = msg.visualDiagram,
                    style = CodeTextStyle.copy(fontSize = 10.5.sp, color = NeonCyan),
                    modifier = Modifier.padding(6.dp)
                  )
                }
              }

              if (msg.complexity.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = "Complexity: ${msg.complexity}",
                  style = ComplexityBadgeStyle.copy(fontSize = 10.sp, color = AccentGreen)
                )
              }
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Input Bar
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      OutlinedTextField(
        value = userInput,
        onValueChange = { userInput = it },
        placeholder = { Text("Ask a question about data structures...") },
        modifier = Modifier.weight(1f),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = NeonCyan,
          unfocusedBorderColor = MaterialTheme.colorScheme.outline
        )
      )

      Spacer(modifier = Modifier.width(8.dp))

      IconButton(
        onClick = {
          if (userInput.isNotBlank()) {
            val q = userInput.trim()
            userInput = ""
            processUserQuestion(q) { answer, diag, comp ->
              messages = messages + TutorMessage("USER", q) + TutorMessage("TUTOR", answer, diag, comp)
            }
          }
        },
        modifier = Modifier
          .clip(CircleShape)
          .background(ElectricBlue)
      ) {
        Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.Black)
      }
    }
  }
}

fun processUserQuestion(
  question: String,
  onResult: (answer: String, diagram: String, complexity: String) -> Unit
) {
  val q = question.lowercase()
  when {
    q.contains("linked list") -> {
      onResult(
        "A Linked List stores elements in dynamically allocated nodes scattered across RAM, where each node points to the next node using a pointer address. Inserting at the head requires only 1 pointer update with O(1) time!",
        "HEAD -> [Data: 10 | Next: 0x40] -> [Data: 20 | Next: NULL]",
        "Prepend: O(1), Search: O(n), Space: O(n)"
      )
    }
    q.contains("binary search") -> {
      onResult(
        "Binary search repeatedly divides the search space in half. For 1,000,000 items, it takes at most ~20 comparisons because 2^20 ≈ 1,000,000!",
        "1000 items -> 500 -> 250 -> 125 -> 64 -> 32 -> 16 -> 8 -> 4 -> 2 -> 1",
        "Time: O(log₂ n), Space: O(1) iterative"
      )
    }
    q.contains("bfs") || q.contains("breadth") -> {
      onResult(
        "Breadth-First Search (BFS) explores all neighboring nodes at the current depth before moving to the next level. It uses a FIFO Queue to ensure level-order traversal and guarantees the shortest path in unweighted graphs!",
        "Level 0 (Root) -> Level 1 (Immediate Neighbors) -> Level 2",
        "Time: O(V + E), Space: O(V) for Queue"
      )
    }
    q.contains("hash") -> {
      onResult(
        "A Hash Table maps keys to bucket indices using a hash function. It enables instantaneous key lookups by converting string keys into direct memory numbers!",
        "\"apple\" -> hash(\"apple\") -> 42 -> 42 % 10 = index [2]",
        "Average Time: O(1) Get/Put, Worst Case: O(n)"
      )
    }
    q.contains("recursion") -> {
      onResult(
        "Recursion is when a function calls itself to solve smaller subproblems until hitting a base case. Each call pushes a stack frame onto the memory call stack, which unwinds in reverse (LIFO).",
        "factorial(3) -> pushes stack -> factorial(2) -> factorial(1) -> base case unwinds",
        "Time: O(n), Space: O(n) call stack frames"
      )
    }
    else -> {
      onResult(
        "In computer science, data structures represent how data is organized in physical hardware RAM. Choosing the optimal structure reduces asymptotic Big-O runtime and prevents system bottlenecks.",
        "Algorithm + Data Structure = Efficient Software",
        "Time vs Space tradeoffs dictate design"
      )
    }
  }
}
