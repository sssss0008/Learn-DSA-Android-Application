package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun ComplexityBadge(
  label: String,
  complexity: String,
  color: Color = NeonCyan,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(8.dp),
    color = color.copy(alpha = 0.12f),
    border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.4f)),
    modifier = modifier.testTag("complexity_badge_${label.lowercase()}")
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
      Text(
        text = "$label: ",
        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
      )
      Text(
        text = complexity,
        style = ComplexityBadgeStyle.copy(color = color)
      )
    }
  }
}

@Composable
fun ArrayCellView(
  index: Int,
  value: Int,
  isHighlighted: Boolean = false,
  isComparing: Boolean = false,
  isTarget: Boolean = false,
  memoryAddress: String = "",
  modifier: Modifier = Modifier
) {
  val backgroundColor by animateColorAsState(
    targetValue = when {
      isTarget -> AccentGreen.copy(alpha = 0.35f)
      isComparing -> WarningAmber.copy(alpha = 0.35f)
      isHighlighted -> ElectricBlue.copy(alpha = 0.35f)
      else -> MaterialTheme.colorScheme.surfaceVariant
    },
    animationSpec = tween(durationMillis = 300),
    label = "array_cell_bg"
  )

  val borderColor = when {
    isTarget -> AccentGreen
    isComparing -> WarningAmber
    isHighlighted -> ElectricBlue
    else -> MaterialTheme.colorScheme.outline
  }

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .width(58.dp)
      .testTag("array_cell_$index")
  ) {
    if (memoryAddress.isNotEmpty()) {
      Text(
        text = memoryAddress,
        style = MemoryAddressStyle.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
        maxLines = 1,
        fontSize = 9.sp,
        modifier = Modifier.padding(bottom = 2.dp)
      )
    }

    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier
        .size(54.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(backgroundColor)
        .border(1.5.dp, borderColor, RoundedCornerShape(10.dp))
    ) {
      Text(
        text = value.toString(),
        style = CodeTextStyle.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp,
          color = if (isTarget) AccentGreen else MaterialTheme.colorScheme.onSurface
        )
      )
    }

    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "[$index]",
      style = ComplexityBadgeStyle.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
      fontSize = 11.sp
    )
  }
}

@Composable
fun CodeBlockView(
  code: String,
  language: String = "Python",
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    modifier = modifier
      .fillMaxWidth()
      .testTag("code_block_$language")
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          shape = RoundedCornerShape(6.dp),
          color = ElectricBlue.copy(alpha = 0.2f),
          border = androidx.compose.foundation.BorderStroke(0.8.dp, ElectricBlue.copy(alpha = 0.5f))
        ) {
          Text(
            text = language.uppercase(),
            style = CodeTextStyle.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp, color = ElectricBlue),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }
        Icon(
          imageVector = Icons.Default.Code,
          contentDescription = "Code snippet",
          tint = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.size(16.dp)
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = code,
        style = CodeTextStyle.copy(color = MaterialTheme.colorScheme.onSurface, fontSize = 12.5.sp),
        modifier = Modifier.fillMaxWidth()
      )
    }
  }
}

@Composable
fun OperationControls(
  isPlaying: Boolean,
  canStepBack: Boolean,
  canStepForward: Boolean,
  speed: Float,
  onPlayPauseToggle: () -> Unit,
  onStepBack: () -> Unit,
  onStepForward: () -> Unit,
  onSpeedToggle: () -> Unit,
  onReset: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(16.dp),
    color = MaterialTheme.colorScheme.surfaceVariant,
    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    modifier = modifier.testTag("operation_controls")
  ) {
    Row(
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
      IconButton(
        onClick = onReset,
        modifier = Modifier.testTag("btn_reset")
      ) {
        Icon(Icons.Default.Refresh, contentDescription = "Reset lab", tint = MaterialTheme.colorScheme.onSurface)
      }

      IconButton(
        onClick = onStepBack,
        enabled = canStepBack,
        modifier = Modifier.testTag("btn_step_back")
      ) {
        Icon(
          Icons.Default.SkipPrevious,
          contentDescription = "Step backward",
          tint = if (canStepBack) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
        )
      }

      FloatingActionButton(
        onClick = onPlayPauseToggle,
        containerColor = ElectricBlue,
        contentColor = Color.Black,
        shape = CircleShape,
        modifier = Modifier
          .size(46.dp)
          .testTag("btn_play_pause")
      ) {
        Icon(
          imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
          contentDescription = if (isPlaying) "Pause animation" else "Play animation"
        )
      }

      IconButton(
        onClick = onStepForward,
        enabled = canStepForward,
        modifier = Modifier.testTag("btn_step_forward")
      ) {
        Icon(
          Icons.Default.SkipNext,
          contentDescription = "Step forward",
          tint = if (canStepForward) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
        )
      }

      TextButton(
        onClick = onSpeedToggle,
        modifier = Modifier.testTag("btn_speed_toggle")
      ) {
        Text(
          text = "${speed}x",
          style = CodeTextStyle.copy(fontWeight = FontWeight.Bold, color = NeonCyan)
        )
      }
    }
  }
}

@Composable
fun CreatorCard(
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val linkedInUrl = "https://www.linkedin.com/in/awiskaracharya/"

  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceVariant
    ),
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      Brush.horizontalGradient(listOf(ElectricBlue, NeonCyan, TechPurple))
    ),
    modifier = modifier
      .fillMaxWidth()
      .testTag("creator_card")
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(Brush.linearGradient(listOf(MidnightBlue, ElectricViolet)))
          ) {
            Text(
              text = "AA",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = NeonCyan)
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Text(
              text = "Awiskar Acharya",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = "Creator & Computer Science Educator",
              style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
          }
        }

        Surface(
          shape = RoundedCornerShape(20.dp),
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

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = "“Understand. Visualize. Code. Master Data Structures.”",
        style = MaterialTheme.typography.bodyMedium.copy(
          color = ElectricBlue,
          fontWeight = FontWeight.Medium
        )
      )

      Text(
        text = "Learn freely. Practice freely. Build freely. No paywalls, no subscriptions.",
        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
        modifier = Modifier.padding(vertical = 4.dp)
      )

      Spacer(modifier = Modifier.height(8.dp))

      Button(
        onClick = {
          val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedInUrl))
          context.startActivity(intent)
        },
        colors = ButtonDefaults.buttonColors(
          containerColor = MidnightBlue,
          contentColor = NeonCyan
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("btn_connect_linkedin")
      ) {
        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Connect with Awiskar Acharya on LinkedIn")
      }
    }
  }
}
