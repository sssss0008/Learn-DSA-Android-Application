package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.UserProfileEntity
import com.example.ui.components.ComplexityBadge
import com.example.ui.components.CreatorCard
import com.example.ui.theme.*

@Composable
fun HomeScreen(
  profile: UserProfileEntity,
  onNavigateToLearn: () -> Unit,
  onNavigateToVisualizer: () -> Unit,
  onNavigateToPractice: () -> Unit,
  onOpenAiTutor: () -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .testTag("home_screen"),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Top Greeting
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Welcome back, ${profile.name}",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "Continue building your programming skills.",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )
        }

        IconButton(
          onClick = onOpenAiTutor,
          modifier = Modifier
            .clip(CircleShape)
            .background(MidnightBlue)
            .testTag("btn_ai_tutor_home")
        ) {
          Icon(Icons.Default.Psychology, contentDescription = "AI Tutor", tint = NeonCyan)
        }
      }
    }

    // Main Card: Continue Learning
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = androidx.compose.foundation.BorderStroke(1.dp, ElectricBlue.copy(alpha = 0.5f)),
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onNavigateToLearn() }
          .testTag("continue_learning_card")
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = ElectricBlue.copy(alpha = 0.15f)
            ) {
              Text(
                text = "CONTINUE LEARNING",
                style = ComplexityBadgeStyle.copy(color = ElectricBlue, fontSize = 10.sp),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }
            Text(
              text = "Level ${profile.currentLevel} of 20",
              style = CodeTextStyle.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "Arrays — Insertion, Deletion & Pointers",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "Master O(1) index arithmetic and O(n) shifting operations.",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            LinearProgressIndicator(
              progress = { 0.65f },
              modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
              color = NeonCyan,
              trackColor = MaterialTheme.colorScheme.surface
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
              text = "65%",
              style = CodeTextStyle.copy(fontWeight = FontWeight.Bold, color = NeonCyan)
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = onNavigateToLearn,
            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = Color.Black),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(text = "Continue Lesson", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.PlayArrow, contentDescription = null)
          }
        }
      }
    }

    // Streak & Quick Stats
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // Streak Card
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
          border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
          modifier = Modifier.weight(1f)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = WarningAmber)
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "${profile.currentStreak} Days",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
              )
            }
            Text(
              text = "Active Learning Streak",
              style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              listOf("M", "T", "W", "T", "F", "S", "S").forEachIndexed { idx, day ->
                val active = idx < profile.currentStreak.coerceAtMost(7)
                Text(
                  text = day,
                  style = CodeTextStyle.copy(
                    fontSize = 11.sp,
                    fontWeight = if (active) FontWeight.Bold else FontWeight.Normal,
                    color = if (active) WarningAmber else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                  )
                )
              }
            }
          }
        }

        // Points Card
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
          border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
          modifier = Modifier.weight(1f)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.WorkspacePremium, contentDescription = null, tint = NeonCyan)
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "${profile.totalPoints} pts",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
              )
            }
            Text(
              text = "Mastery Score",
              style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Rank: Code Apprentice",
              style = CodeTextStyle.copy(fontSize = 11.sp, color = NeonCyan)
            )
          }
        }
      }
    }

    // Daily Challenge Section
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = androidx.compose.foundation.BorderStroke(1.dp, TechPurple.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Bolt, contentDescription = null, tint = WarningAmber)
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Daily Challenge",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
              )
            }
            ComplexityBadge(label = "Points", complexity = "+50", color = WarningAmber)
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "Invert a Binary Tree",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
          )
          Text(
            text = "Recursively swap left and right subtrees in O(n) time.",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            OutlinedButton(
              onClick = onNavigateToPractice,
              shape = RoundedCornerShape(8.dp)
            ) {
              Text("Solve Problem")
            }
            Button(
              onClick = onNavigateToVisualizer,
              colors = ButtonDefaults.buttonColors(containerColor = TechPurple),
              shape = RoundedCornerShape(8.dp)
            ) {
              Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Visualize Tree")
            }
          }
        }
      }
    }

    // Visual Lab Spotlight
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MidnightBlue),
        border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan.copy(alpha = 0.4f)),
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onNavigateToVisualizer() }
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Data Structure Visual Lab",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = NeonCyan)
            )
            Text(
              text = "See, build, modify, break, debug, and code every structure in real-time.",
              style = MaterialTheme.typography.bodySmall.copy(color = TextSecondaryDark)
            )
          }
          Icon(Icons.Default.ArrowForward, contentDescription = null, tint = NeonCyan)
        }
      }
    }

    // Creator Spotlight & Free Education message
    item {
      CreatorCard()
    }
  }
}
