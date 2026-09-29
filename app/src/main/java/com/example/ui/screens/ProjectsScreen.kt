package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
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
import com.example.data.model.MiniProject
import com.example.data.repository.DsRepository
import com.example.ui.components.ComplexityBadge
import com.example.ui.theme.*

@Composable
fun ProjectsScreen(
  onPointsEarned: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedProject by remember { mutableStateOf<MiniProject?>(null) }
  var completedTasks by remember { mutableStateOf(setOf<Int>()) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(14.dp)
      .testTag("projects_screen")
  ) {
    if (selectedProject != null) {
      val proj = selectedProject!!
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
          IconButton(onClick = { selectedProject = null }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
          }
          ComplexityBadge(label = "Primary DS", complexity = proj.primaryDs, color = NeonCyan)
        }

        Text(
          text = proj.title,
          style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
          text = proj.subtitle,
          style = MaterialTheme.typography.bodyMedium.copy(color = ElectricBlue)
        )

        Spacer(modifier = Modifier.height(14.dp))
        Text(
          text = "Project Description:",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
        Text(text = proj.description, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(14.dp))
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
          border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Text(
              text = "Architecture & Data Structure Design:",
              style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold, color = NeonCyan)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = proj.architecture, style = CodeTextStyle.copy(fontSize = 12.sp))
          }
        }

        Spacer(modifier = Modifier.height(14.dp))
        Text(
          text = "Implementation Checklist:",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )

        proj.keyOperations.forEachIndexed { idx, op ->
          val isChecked = completedTasks.contains(idx)
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
              .fillMaxWidth()
              .clickable {
                completedTasks = if (isChecked) completedTasks - idx else completedTasks + idx
                if (!isChecked) onPointsEarned(25)
              }
              .padding(vertical = 6.dp)
          ) {
            Checkbox(
              checked = isChecked,
              onCheckedChange = {
                completedTasks = if (it) completedTasks + idx else completedTasks - idx
                if (it) onPointsEarned(25)
              }
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = op, style = CodeTextStyle.copy(fontWeight = FontWeight.SemiBold))
          }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Card(
          shape = RoundedCornerShape(10.dp),
          colors = CardDefaults.cardColors(containerColor = MidnightBlue),
          border = androidx.compose.foundation.BorderStroke(1.dp, ElectricBlue.copy(alpha = 0.5f))
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Text(
              text = "Interactive Simulation Hint:",
              style = MaterialTheme.typography.labelLarge.copy(color = ElectricBlue, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = proj.interactiveSimulationHint, style = MaterialTheme.typography.bodySmall)
          }
        }
      }
    } else {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Computer Science Projects",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "Build real software architectures with data structures",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )
        }
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = NeonCyan.copy(alpha = 0.15f),
          border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan.copy(alpha = 0.4f))
        ) {
          Text(
            text = "7 LABS",
            style = ComplexityBadgeStyle.copy(color = NeonCyan, fontSize = 10.sp),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(DsRepository.miniProjects) { proj ->
          Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            modifier = Modifier
              .fillMaxWidth()
              .clickable { selectedProject = proj }
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = proj.title,
                  style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                ComplexityBadge(label = "DS", complexity = proj.primaryDs, color = NeonCyan)
              }

              Spacer(modifier = Modifier.height(4.dp))
              Text(text = proj.subtitle, style = MaterialTheme.typography.bodySmall.copy(color = ElectricBlue))

              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = proj.description,
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                maxLines = 2
              )

              Spacer(modifier = Modifier.height(10.dp))
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
              ) {
                Text(
                  text = "Open Project Workspace →",
                  style = CodeTextStyle.copy(fontWeight = FontWeight.Bold, color = NeonCyan, fontSize = 12.sp)
                )
              }
            }
          }
        }
      }
    }
  }
}
