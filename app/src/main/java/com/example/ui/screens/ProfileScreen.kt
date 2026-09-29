package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.UserProfileEntity
import com.example.data.repository.DsRepository
import com.example.ui.components.ComplexityBadge
import com.example.ui.components.CreatorCard
import com.example.ui.theme.*

@Composable
fun ProfileScreen(
  profile: UserProfileEntity,
  currentTheme: AppThemeMode,
  onThemeChange: (AppThemeMode) -> Unit,
  onResetProgress: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  var showCertificate by remember { mutableStateOf(false) }
  var showGlossary by remember { mutableStateOf(false) }

  if (showCertificate) {
    CertificateModal(
      name = profile.name,
      points = profile.totalPoints,
      onClose = { showCertificate = false }
    )
    return
  }

  if (showGlossary) {
    GlossaryModal(onClose = { showGlossary = false })
    return
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(14.dp)
      .testTag("profile_screen"),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Profile Header Card
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
          ) {
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(MidnightBlue, ElectricBlue)))
                .border(2.dp, NeonCyan, CircleShape)
            ) {
              Text(
                text = profile.name.take(1).uppercase(),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, color = NeonCyan)
              )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
              Text(
                text = profile.name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
              )
              Text(
                text = "${profile.experience} • Goal: ${profile.learningGoal}",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
              )
              Text(
                text = "Language: ${profile.selectedLanguages}",
                style = CodeTextStyle.copy(color = NeonCyan, fontSize = 11.sp)
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))
          HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
          Spacer(modifier = Modifier.height(14.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(text = "Streak", style = MaterialTheme.typography.bodySmall)
              Text(text = "${profile.currentStreak} Days", style = CodeTextStyle.copy(fontWeight = FontWeight.Bold, color = WarningAmber, fontSize = 15.sp))
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(text = "Total Score", style = MaterialTheme.typography.bodySmall)
              Text(text = "${profile.totalPoints} pts", style = CodeTextStyle.copy(fontWeight = FontWeight.Bold, color = NeonCyan, fontSize = 15.sp))
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(text = "Level", style = MaterialTheme.typography.bodySmall)
              Text(text = "${profile.currentLevel} / 20", style = CodeTextStyle.copy(fontWeight = FontWeight.Bold, color = ElectricBlue, fontSize = 15.sp))
            }
          }
        }
      }
    }

    // Official Certificate Card
    item {
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MidnightBlue),
        border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan.copy(alpha = 0.6f)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Verified, contentDescription = null, tint = NeonCyan)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Course Certificate",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
              )
            }
            Surface(
              shape = RoundedCornerShape(4.dp),
              color = AccentGreen.copy(alpha = 0.2f)
            ) {
              Text(
                text = "OFFICIAL",
                style = ComplexityBadgeStyle.copy(color = AccentGreen, fontSize = 9.sp),
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "Generate and inspect your verified Data Structures completion credential certified by Awiskar Acharya.",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )

          Spacer(modifier = Modifier.height(10.dp))
          Button(
            onClick = { showCertificate = true },
            colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(Icons.Default.School, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("View Certificate of Mastery", fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // Skill Map Tree
    item {
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Skill Mastery Tree",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(10.dp))

          val skills = listOf(
            "Foundations" to true,
            "Arrays & Memory" to true,
            "Linked Lists" to true,
            "Stacks & Queues" to true,
            "Hash Tables" to false,
            "Trees & BST" to false,
            "Heaps & Graphs" to false
          )

          skills.forEach { (skill, mastered) ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = skill, style = MaterialTheme.typography.bodyMedium)
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = if (mastered) AccentGreen.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface
              ) {
                Text(
                  text = if (mastered) "MASTERED" else "IN PROGRESS",
                  style = ComplexityBadgeStyle.copy(
                    fontSize = 9.sp,
                    color = if (mastered) AccentGreen else MaterialTheme.colorScheme.onSurfaceVariant
                  ),
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }
          }
        }
      }
    }

    // Glossary & Cheat Sheet Shortcut
    item {
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = Modifier
          .fillMaxWidth()
          .clickable { showGlossary = true }
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.MenuBook, contentDescription = null, tint = ElectricBlue)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "Data Structures Glossary",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
              )
              Text(
                text = "30+ Definitions, memory formulas & Big-O terms",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
              )
            }
          }
          Icon(Icons.Default.ArrowForward, contentDescription = null, tint = ElectricBlue)
        }
      }
    }

    // Creator Spotlight & LinkedIn CTA
    item {
      CreatorCard()
    }

    // Settings & Theme Switcher
    item {
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Settings & Appearance",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(10.dp))

          Text(text = "App Theme Mode:", style = MaterialTheme.typography.bodySmall)
          Spacer(modifier = Modifier.height(6.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            AppThemeMode.values().forEach { mode ->
              val isSelected = currentTheme == mode
              Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                  .weight(1f)
                  .clip(RoundedCornerShape(8.dp))
                  .background(if (isSelected) MidnightBlue else MaterialTheme.colorScheme.surface)
                  .border(1.dp, if (isSelected) NeonCyan else MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                  .clickable { onThemeChange(mode) }
                  .padding(vertical = 8.dp)
              ) {
                Text(
                  text = mode.name.replace("_", " "),
                  style = CodeTextStyle.copy(
                    fontSize = 9.5.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) NeonCyan else MaterialTheme.colorScheme.onSurfaceVariant
                  )
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          OutlinedButton(
            onClick = onResetProgress,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorCrimson),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(Icons.Default.RestartAlt, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Reset Learning Journey")
          }
        }
      }
    }
  }
}

@Composable
fun CertificateModal(
  name: String,
  points: Int,
  onClose: () -> Unit
) {
  val context = LocalContext.current
  val linkedInUrl = "https://www.linkedin.com/in/awiskaracharya/"

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(16.dp)
      .verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onClose) {
        Icon(Icons.Default.Close, contentDescription = "Close")
      }
      Text(
        text = "Verified Credential",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
      Spacer(modifier = Modifier.width(48.dp))
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Certificate Frame
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
      border = androidx.compose.foundation.BorderStroke(2.dp, Brush.linearGradient(listOf(NeonCyan, ElectricBlue, ElectricViolet))),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Icon(Icons.Default.WorkspacePremium, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(54.dp))
        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "CERTIFICATE OF MASTERY",
          style = CodeTextStyle.copy(fontWeight = FontWeight.Bold, color = NeonCyan, fontSize = 14.sp)
        )
        Text(
          text = "Learn Data Structures",
          style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
          text = "This credential is proudly presented to",
          style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = name,
          style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold, color = ElectricBlue)
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(
          text = "For successfully completing interactive visual labs, code implementations, debugging exercises, and complexity analysis across linear and hierarchical data structures.",
          style = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Center),
          modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        Spacer(modifier = Modifier.height(14.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.Bottom
        ) {
          Column {
            Text(text = "Instructor & Creator", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
            Text(text = "Awiskar Acharya", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
          }
          Column(horizontalAlignment = Alignment.End) {
            Text(text = "Certificate ID", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
            Text(text = "DS-AA-${points}-VERIFIED", style = CodeTextStyle.copy(color = NeonCyan, fontSize = 11.sp))
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    Button(
      onClick = {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedInUrl))
        context.startActivity(intent)
      },
      colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = Color.Black),
      shape = RoundedCornerShape(10.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Icon(Icons.Default.Share, contentDescription = null)
      Spacer(modifier = Modifier.width(8.dp))
      Text("Share on LinkedIn & Message Awiskar Acharya", fontWeight = FontWeight.Bold)
    }
  }
}

@Composable
fun GlossaryModal(onClose: () -> Unit) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(14.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onClose) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
      }
      Text(text = "Data Structures Glossary", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
      Spacer(modifier = Modifier.width(48.dp))
    }

    Spacer(modifier = Modifier.height(10.dp))

    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      items(DsRepository.glossaryTerms) { term ->
        Card(
          shape = RoundedCornerShape(10.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = term.term, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
              Surface(
                shape = RoundedCornerShape(4.dp),
                color = MidnightBlue
              ) {
                Text(
                  text = term.category,
                  style = ComplexityBadgeStyle.copy(color = NeonCyan, fontSize = 9.sp),
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = term.definition, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Notation: ${term.formulaOrNotation}", style = CodeTextStyle.copy(color = ElectricBlue, fontSize = 11.sp))
          }
        }
      }
    }
  }
}
