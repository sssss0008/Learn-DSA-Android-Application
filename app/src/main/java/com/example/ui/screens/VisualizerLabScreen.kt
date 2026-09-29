package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.data.repository.DsRepository
import com.example.ui.components.ArrayCellView
import com.example.ui.components.ComplexityBadge
import com.example.ui.components.OperationControls
import com.example.ui.theme.*

@Composable
fun VisualizerLabScreen(
  initialType: String = "ARRAY",
  modifier: Modifier = Modifier
) {
  var selectedType by remember {
    mutableStateOf(
      try {
        VisualizerType.valueOf(initialType)
      } catch (e: Exception) {
        VisualizerType.ARRAY
      }
    )
  }

  // Animation & Playback states
  var isPlaying by remember { mutableStateOf(false) }
  var currentStep by remember { mutableIntStateOf(0) }
  var speed by remember { mutableFloatStateOf(1.0f) }

  // Array Visualizer State
  var arrayItems by remember { mutableStateOf(listOf(12, 25, 31, 44, 58)) }
  var arrayHighlightedIndex by remember { mutableStateOf<Int?>(null) }
  var arrayTargetIndex by remember { mutableStateOf<Int?>(null) }
  var inputVal by remember { mutableStateOf("99") }
  var operationStatus by remember { mutableStateOf("Ready for operations") }

  // Linked List State
  var listNodes by remember { mutableStateOf(listOf(10, 20, 30, 40)) }
  var listHighlightedNode by remember { mutableStateOf<Int?>(null) }

  // Stack State
  var stackItems by remember { mutableStateOf(listOf(10, 20, 30)) }

  // Queue State
  var queueItems by remember { mutableStateOf(listOf(10, 20, 30, 40)) }

  // BST State
  var bstNodes by remember { mutableStateOf(listOf(50, 30, 70, 20, 40, 60, 80)) }
  var bstHighlightVal by remember { mutableStateOf<Int?>(null) }
  var bstTraversalResult by remember { mutableStateOf("") }

  // Hash Table State
  var hashBuckets by remember {
    mutableStateOf<List<List<Pair<String, Int>>>>(
      listOf(
        emptyList(),
        listOf("apple" to 10),
        emptyList(),
        listOf("banana" to 25, "cherry" to 30),
        emptyList(),
        emptyList()
      )
    )
  }

  // Sorting Visualizer State
  var sortArray by remember { mutableStateOf(listOf(64, 34, 25, 12, 22, 11, 90)) }
  var comparisonsCount by remember { mutableStateOf(0) }
  var swapsCount by remember { mutableStateOf(0) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(12.dp)
      .testTag("visualizer_lab_screen")
  ) {
    // Structure Tabs
    ScrollableTabRow(
      selectedTabIndex = selectedType.ordinal,
      edgePadding = 4.dp,
      containerColor = MaterialTheme.colorScheme.background,
      contentColor = ElectricBlue,
      modifier = Modifier.fillMaxWidth()
    ) {
      VisualizerType.values().forEach { type ->
        Tab(
          selected = selectedType == type,
          onClick = {
            selectedType = type
            operationStatus = "Switched to ${type.title}"
            arrayHighlightedIndex = null
            arrayTargetIndex = null
          },
          text = {
            Text(
              text = type.title,
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = if (selectedType == type) FontWeight.Bold else FontWeight.Normal,
                color = if (selectedType == type) NeonCyan else MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Visual Workspace Card
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
      border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(14.dp)
          .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Workspace Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = selectedType.title,
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = operationStatus,
              style = CodeTextStyle.copy(color = NeonCyan, fontSize = 11.sp)
            )
          }

          Surface(
            shape = RoundedCornerShape(6.dp),
            color = MidnightBlue
          ) {
            Text(
              text = selectedType.badge,
              style = ComplexityBadgeStyle.copy(color = ElectricBlue, fontSize = 10.sp),
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Dynamic visual component based on selected type
        when (selectedType) {
          VisualizerType.ARRAY -> {
            // Array visualizer
            Text(
              text = "MEMORY LAYOUT (Contiguous Hardware Addresses)",
              style = CodeTextStyle.copy(fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
              modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
              horizontalArrangement = Arrangement.Center
            ) {
              arrayItems.forEachIndexed { idx, valItem ->
                ArrayCellView(
                  index = idx,
                  value = valItem,
                  isHighlighted = (arrayHighlightedIndex == idx),
                  isTarget = (arrayTargetIndex == idx),
                  memoryAddress = "0x${(1000 + idx * 4).toString(16).uppercase()}"
                )
                if (idx < arrayItems.size - 1) {
                  Spacer(modifier = Modifier.width(6.dp))
                }
              }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              ComplexityBadge(label = "Search", complexity = "O(n)", color = WarningAmber)
              ComplexityBadge(label = "Insert at End", complexity = "O(1)*", color = AccentGreen)
              ComplexityBadge(label = "Direct Access", complexity = "O(1)", color = NeonCyan)
            }
          }

          VisualizerType.LINKED_LIST -> {
            // Singly Linked List Visualizer
            Text(
              text = "HEAD POINTER -> DYNAMIC HEAP NODES",
              style = CodeTextStyle.copy(fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
              modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = ElectricBlue.copy(alpha = 0.2f),
                border = androidx.compose.foundation.BorderStroke(1.dp, ElectricBlue)
              ) {
                Text(
                  text = "HEAD",
                  style = CodeTextStyle.copy(fontWeight = FontWeight.Bold, color = ElectricBlue),
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                )
              }

              Icon(
                Icons.Default.ArrowForward,
                contentDescription = null,
                tint = NeonCyan,
                modifier = Modifier.padding(horizontal = 6.dp)
              )

              listNodes.forEachIndexed { idx, valItem ->
                val isHighlighted = listHighlightedNode == idx
                Card(
                  shape = RoundedCornerShape(10.dp),
                  colors = CardDefaults.cardColors(
                    containerColor = if (isHighlighted) ElectricBlue.copy(alpha = 0.35f) else MaterialTheme.colorScheme.surface
                  ),
                  border = androidx.compose.foundation.BorderStroke(
                    1.5.dp,
                    if (isHighlighted) NeonCyan else MaterialTheme.colorScheme.outline
                  )
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                      Text(
                        text = "val: $valItem",
                        style = CodeTextStyle.copy(fontWeight = FontWeight.Bold)
                      )
                      Text(
                        text = "0x${(4000 + idx * 32).toString(16).uppercase()}",
                        style = MemoryAddressStyle.copy(fontSize = 9.sp)
                      )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "| *", style = CodeTextStyle.copy(color = NeonCyan))
                  }
                }

                Icon(
                  Icons.Default.ArrowForward,
                  contentDescription = null,
                  tint = NeonCyan,
                  modifier = Modifier.padding(horizontal = 6.dp)
                )
              }

              Surface(
                shape = RoundedCornerShape(6.dp),
                color = ErrorCrimson.copy(alpha = 0.15f),
                border = androidx.compose.foundation.BorderStroke(1.dp, ErrorCrimson.copy(alpha = 0.5f))
              ) {
                Text(
                  text = "NULL",
                  style = CodeTextStyle.copy(fontWeight = FontWeight.Bold, color = ErrorCrimson),
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              ComplexityBadge(label = "Insert Head", complexity = "O(1)", color = AccentGreen)
              ComplexityBadge(label = "Search", complexity = "O(n)", color = WarningAmber)
              ComplexityBadge(label = "Delete Node", complexity = "O(1)*", color = NeonCyan)
            }
          }

          VisualizerType.STACK -> {
            // Stack Visualizer (Vertical LIFO)
            Text(
              text = "LAST IN, FIRST OUT (LIFO) CONTAINER",
              style = CodeTextStyle.copy(fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              modifier = Modifier.width(160.dp)
            ) {
              if (stackItems.isEmpty()) {
                Text("Stack is Empty (Underflow)", style = CodeTextStyle.copy(color = WarningAmber))
              } else {
                stackItems.reversed().forEachIndexed { revIdx, valItem ->
                  val isTop = revIdx == 0
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                  ) {
                    if (isTop) {
                      Text(
                        text = "TOP →",
                        style = CodeTextStyle.copy(color = NeonCyan, fontWeight = FontWeight.Bold, fontSize = 11.sp),
                        modifier = Modifier.width(45.dp)
                      )
                    } else {
                      Spacer(modifier = Modifier.width(45.dp))
                    }

                    Box(
                      contentAlignment = Alignment.Center,
                      modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 3.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isTop) MidnightBlue else MaterialTheme.colorScheme.surface)
                        .border(1.5.dp, if (isTop) NeonCyan else MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                        .padding(vertical = 10.dp)
                    ) {
                      Text(
                        text = valItem.toString(),
                        style = CodeTextStyle.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp)
                      )
                    }
                  }
                }
              }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              ComplexityBadge(label = "Push", complexity = "O(1)", color = AccentGreen)
              ComplexityBadge(label = "Pop", complexity = "O(1)", color = AccentGreen)
              ComplexityBadge(label = "Peek", complexity = "O(1)", color = NeonCyan)
            }
          }

          VisualizerType.QUEUE -> {
            // Queue Visualizer (Horizontal FIFO)
            Text(
              text = "FIRST IN, FIRST OUT (FIFO) CONTAINER",
              style = CodeTextStyle.copy(fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
              Text(
                text = "FRONT\n(Dequeue)",
                style = CodeTextStyle.copy(color = WarningAmber, fontSize = 10.sp, textAlign = TextAlign.Center)
              )
              Spacer(modifier = Modifier.width(8.dp))

              queueItems.forEachIndexed { idx, valItem ->
                Box(
                  contentAlignment = Alignment.Center,
                  modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.5.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                ) {
                  Text(text = valItem.toString(), style = CodeTextStyle.copy(fontWeight = FontWeight.Bold))
                }
              }

              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "REAR\n(Enqueue)",
                style = CodeTextStyle.copy(color = AccentGreen, fontSize = 10.sp, textAlign = TextAlign.Center)
              )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              ComplexityBadge(label = "Enqueue", complexity = "O(1)", color = AccentGreen)
              ComplexityBadge(label = "Dequeue", complexity = "O(1)", color = AccentGreen)
              ComplexityBadge(label = "Space", complexity = "O(n)", color = ElectricBlue)
            }
          }

          VisualizerType.BST -> {
            // Binary Search Tree Visualizer
            Text(
              text = "INVARIANT: Left < Root < Right",
              style = CodeTextStyle.copy(fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Canvas drawing of BST nodes and links
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(MidnightBlue.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            ) {
              Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val rootPos = Offset(w / 2f, 30f)
                val leftPos = Offset(w / 4f, 85f)
                val rightPos = Offset(3f * w / 4f, 85f)
                val leftLeftPos = Offset(w / 8f, 135f)
                val leftRightPos = Offset(3f * w / 8f, 135f)
                val rightLeftPos = Offset(5f * w / 8f, 135f)
                val rightRightPos = Offset(7f * w / 8f, 135f)

                // Draw connecting branches
                drawLine(Color(0xFF38B6FF), rootPos, leftPos, strokeWidth = 3f)
                drawLine(Color(0xFF38B6FF), rootPos, rightPos, strokeWidth = 3f)
                drawLine(Color(0xFF00F5D4), leftPos, leftLeftPos, strokeWidth = 2.5f)
                drawLine(Color(0xFF00F5D4), leftPos, leftRightPos, strokeWidth = 2.5f)
                drawLine(Color(0xFF00F5D4), rightPos, rightLeftPos, strokeWidth = 2.5f)
                drawLine(Color(0xFF00F5D4), rightPos, rightRightPos, strokeWidth = 2.5f)
              }

              // Display BST Nodes
              Box(modifier = Modifier.fillMaxSize()) {
                BstNodeBadge(value = 50, isHighlighted = (bstHighlightVal == 50), modifier = Modifier.align(Alignment.TopCenter))
                // Level 1
                BstNodeBadge(value = 30, isHighlighted = (bstHighlightVal == 30), modifier = Modifier.padding(start = 65.dp, top = 65.dp))
                BstNodeBadge(value = 70, isHighlighted = (bstHighlightVal == 70), modifier = Modifier.padding(end = 65.dp, top = 65.dp).align(Alignment.TopEnd))
              }
            }

            if (bstTraversalResult.isNotEmpty()) {
              Spacer(modifier = Modifier.height(10.dp))
              Text(
                text = "Visited Sequence: $bstTraversalResult",
                style = CodeTextStyle.copy(color = NeonCyan, fontWeight = FontWeight.Bold)
              )
            }

            Spacer(modifier = Modifier.height(14.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              ComplexityBadge(label = "Search", complexity = "O(log n)", color = NeonCyan)
              ComplexityBadge(label = "Insert", complexity = "O(log n)", color = AccentGreen)
              ComplexityBadge(label = "Worst Case", complexity = "O(n)", color = WarningAmber)
            }
          }

          VisualizerType.HASH_TABLE -> {
            // Hash Table Visualizer
            Text(
              text = "INDEX = hash(key) % capacity",
              style = CodeTextStyle.copy(fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Column(
              modifier = Modifier.fillMaxWidth(),
              verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              for ((bIndex, entries) in hashBuckets.withIndex()) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MidnightBlue,
                    border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan.copy(alpha = 0.5f))
                  ) {
                    Text(
                      text = "Bucket [$bIndex]",
                      style = CodeTextStyle.copy(fontSize = 11.sp, color = NeonCyan),
                      modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                    )
                  }

                  Spacer(modifier = Modifier.width(8.dp))

                  if (entries.isEmpty()) {
                    Text("• empty", style = CodeTextStyle.copy(color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp))
                  } else {
                    for ((k, v) in entries) {
                      Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = androidx.compose.foundation.BorderStroke(0.8.dp, MaterialTheme.colorScheme.outline),
                        modifier = Modifier.padding(end = 6.dp)
                      ) {
                        Text(
                          text = "\"$k\": $v",
                          style = CodeTextStyle.copy(fontSize = 11.sp),
                          modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                        )
                      }
                    }
                  }
                }
              }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              ComplexityBadge(label = "Average Get/Put", complexity = "O(1)", color = AccentGreen)
              ComplexityBadge(label = "Worst Case", complexity = "O(n)", color = WarningAmber)
            }
          }

          VisualizerType.GRAPH -> {
            // Graph & BFS/DFS Visualizer
            Text(
              text = "ADJACENCY GRAPH & TRAVERSAL QUEUE / STACK",
              style = CodeTextStyle.copy(fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(MidnightBlue.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
            ) {
              Canvas(modifier = Modifier.fillMaxSize()) {
                val pA = Offset(size.width * 0.2f, size.height * 0.3f)
                val pB = Offset(size.width * 0.5f, size.height * 0.2f)
                val pC = Offset(size.width * 0.8f, size.height * 0.4f)
                val pD = Offset(size.width * 0.35f, size.height * 0.8f)
                val pE = Offset(size.width * 0.7f, size.height * 0.8f)

                drawLine(Color(0xFF38B6FF), pA, pB, strokeWidth = 3f)
                drawLine(Color(0xFF38B6FF), pB, pC, strokeWidth = 3f)
                drawLine(Color(0xFF38B6FF), pA, pD, strokeWidth = 3f)
                drawLine(Color(0xFF38B6FF), pB, pD, strokeWidth = 3f)
                drawLine(Color(0xFF38B6FF), pC, pE, strokeWidth = 3f)
                drawLine(Color(0xFF38B6FF), pD, pE, strokeWidth = 3f)

                listOf(pA to "A", pB to "B", pC to "C", pD to "D", pE to "E").forEach { (pos, label) ->
                  drawCircle(Color(0xFF00F5D4), radius = 18f, center = pos)
                  drawCircle(Color(0xFF0A1128), radius = 12f, center = pos)
                }
              }
            }

            Spacer(modifier = Modifier.height(14.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              ComplexityBadge(label = "BFS Time", complexity = "O(V + E)", color = NeonCyan)
              ComplexityBadge(label = "DFS Time", complexity = "O(V + E)", color = ElectricBlue)
            }
          }

          VisualizerType.SORTING -> {
            // Sorting suite with comparisons & swaps
            Text(
              text = "SORTING SUITE: Comparisons: $comparisonsCount | Swaps: $swapsCount",
              style = CodeTextStyle.copy(fontSize = 11.sp, color = NeonCyan)
            )
            Spacer(modifier = Modifier.height(14.dp))

            Row(
              modifier = Modifier
                .fillMaxWidth()
                .height(110.dp),
              horizontalArrangement = Arrangement.SpaceEvenly,
              verticalAlignment = Alignment.Bottom
            ) {
              sortArray.forEach { valItem ->
                val barHeight = (valItem * 1.1).dp.coerceIn(20.dp, 100.dp)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  Text(text = valItem.toString(), style = CodeTextStyle.copy(fontSize = 10.sp))
                  Spacer(modifier = Modifier.height(2.dp))
                  Box(
                    modifier = Modifier
                      .width(26.dp)
                      .height(barHeight)
                      .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                      .background(ElectricBlue)
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              ComplexityBadge(label = "Bubble Sort", complexity = "O(n²)", color = WarningAmber)
              ComplexityBadge(label = "Merge/Quick Sort", complexity = "O(n log n)", color = AccentGreen)
            }
          }

          VisualizerType.BIG_O -> {
            // Big-O Lab
            Text(
              text = "ASYMPTOTIC TIME & SPACE COMPLEXITY MATRIX",
              style = CodeTextStyle.copy(fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(modifier = Modifier.height(12.dp))

            DsRepository.complexityMatrix.take(5).forEach { item ->
              Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 3.dp)
              ) {
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(text = item.structureName, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                  Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = "Search: ${item.searchTime}", style = CodeTextStyle.copy(color = NeonCyan, fontSize = 11.sp))
                    Text(text = "Insert: ${item.insertTime}", style = CodeTextStyle.copy(color = ElectricBlue, fontSize = 11.sp))
                  }
                }
              }
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Interactive Action Controls
    Surface(
      shape = RoundedCornerShape(14.dp),
      color = MaterialTheme.colorScheme.surfaceVariant,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(10.dp)) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          when (selectedType) {
            VisualizerType.ARRAY -> {
              Button(
                onClick = {
                  val target = 44
                  val idx = arrayItems.indexOf(target)
                  arrayHighlightedIndex = 0
                  arrayTargetIndex = idx
                  operationStatus = "Searched $target: Found at index $idx (3 comparisons, O(n))"
                },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = Color.Black),
                shape = RoundedCornerShape(8.dp)
              ) {
                Text("Search 44")
              }

              Button(
                onClick = {
                  arrayItems = arrayItems + 99
                  arrayHighlightedIndex = arrayItems.size - 1
                  operationStatus = "Inserted 99 at end: O(1)* amortized"
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black),
                shape = RoundedCornerShape(8.dp)
              ) {
                Text("Insert 99")
              }

              OutlinedButton(
                onClick = {
                  if (arrayItems.isNotEmpty()) {
                    arrayItems = arrayItems.dropLast(1)
                    operationStatus = "Deleted last element: O(1)"
                  }
                },
                shape = RoundedCornerShape(8.dp)
              ) {
                Text("Delete End")
              }

              OutlinedButton(
                onClick = {
                  arrayItems = arrayItems.reversed()
                  operationStatus = "Reversed array in-place O(n)"
                },
                shape = RoundedCornerShape(8.dp)
              ) {
                Text("Reverse")
              }
            }

            VisualizerType.LINKED_LIST -> {
              Button(
                onClick = {
                  listNodes = listOf(5) + listNodes
                  listHighlightedNode = 0
                  operationStatus = "Inserted 5 at HEAD: O(1) pointer assignment"
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black),
                shape = RoundedCornerShape(8.dp)
              ) {
                Text("Insert Head")
              }

              Button(
                onClick = {
                  listNodes = listNodes + 50
                  listHighlightedNode = listNodes.size - 1
                  operationStatus = "Appended 50 to TAIL: O(1) with tail ref"
                },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = Color.Black),
                shape = RoundedCornerShape(8.dp)
              ) {
                Text("Append Tail")
              }

              OutlinedButton(
                onClick = {
                  listNodes = listNodes.reversed()
                  operationStatus = "Reversed linked list in-place: O(n) time, O(1) space"
                },
                shape = RoundedCornerShape(8.dp)
              ) {
                Text("Reverse List")
              }
            }

            VisualizerType.STACK -> {
              Button(
                onClick = {
                  stackItems = stackItems + 40
                  operationStatus = "Pushed 40 onto stack: O(1)"
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black),
                shape = RoundedCornerShape(8.dp)
              ) {
                Text("Push 40")
              }

              OutlinedButton(
                onClick = {
                  if (stackItems.isNotEmpty()) {
                    val popped = stackItems.last()
                    stackItems = stackItems.dropLast(1)
                    operationStatus = "Popped $popped from stack: O(1)"
                  }
                },
                shape = RoundedCornerShape(8.dp)
              ) {
                Text("Pop")
              }
            }

            VisualizerType.QUEUE -> {
              Button(
                onClick = {
                  queueItems = queueItems + 50
                  operationStatus = "Enqueued 50 to REAR: O(1)"
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black),
                shape = RoundedCornerShape(8.dp)
              ) {
                Text("Enqueue 50")
              }

              OutlinedButton(
                onClick = {
                  if (queueItems.isNotEmpty()) {
                    val removed = queueItems.first()
                    queueItems = queueItems.drop(1)
                    operationStatus = "Dequeued $removed from FRONT: O(1)"
                  }
                },
                shape = RoundedCornerShape(8.dp)
              ) {
                Text("Dequeue")
              }
            }

            VisualizerType.BST -> {
              Button(
                onClick = {
                  bstHighlightVal = 70
                  operationStatus = "Found 70: Root 50 -> Right -> 70 (2 steps, O(log n))"
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black),
                shape = RoundedCornerShape(8.dp)
              ) {
                Text("Search 70")
              }

              Button(
                onClick = {
                  bstTraversalResult = "20 -> 30 -> 40 -> 50 -> 60 -> 70 -> 80"
                  operationStatus = "Inorder Traversal: Left -> Root -> Right (Sorted order!)"
                },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = Color.Black),
                shape = RoundedCornerShape(8.dp)
              ) {
                Text("Inorder Traversal")
              }
            }

            VisualizerType.SORTING -> {
              Button(
                onClick = {
                  sortArray = sortArray.sorted()
                  comparisonsCount = 21
                  swapsCount = 14
                  operationStatus = "Sorted array using Merge Sort in O(n log n)!"
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black),
                shape = RoundedCornerShape(8.dp)
              ) {
                Text("Sort Array")
              }

              OutlinedButton(
                onClick = {
                  sortArray = listOf(64, 34, 25, 12, 22, 11, 90).shuffled()
                  comparisonsCount = 0
                  swapsCount = 0
                  operationStatus = "Randomized array elements"
                },
                shape = RoundedCornerShape(8.dp)
              ) {
                Text("Shuffle")
              }
            }

            else -> {
              Button(
                onClick = { operationStatus = "Simulated run complete" },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = Color.Black),
                shape = RoundedCornerShape(8.dp)
              ) {
                Text("Run Operation")
              }
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Operation Controls (Play/Pause, Step Forward/Back, Speed, Reset)
    OperationControls(
      isPlaying = isPlaying,
      canStepBack = currentStep > 0,
      canStepForward = currentStep < 10,
      speed = speed,
      onPlayPauseToggle = {
        isPlaying = !isPlaying
        operationStatus = if (isPlaying) "Animation playing..." else "Animation paused"
      },
      onStepBack = {
        if (currentStep > 0) currentStep--
        operationStatus = "Stepped back to step $currentStep"
      },
      onStepForward = {
        currentStep++
        operationStatus = "Stepped forward to step $currentStep"
      },
      onSpeedToggle = {
        speed = when (speed) {
          1.0f -> 1.5f
          1.5f -> 2.0f
          2.0f -> 0.5f
          else -> 1.0f
        }
      },
      onReset = {
        isPlaying = false
        currentStep = 0
        arrayItems = listOf(12, 25, 31, 44, 58)
        listNodes = listOf(10, 20, 30, 40)
        stackItems = listOf(10, 20, 30)
        queueItems = listOf(10, 20, 30, 40)
        arrayHighlightedIndex = null
        arrayTargetIndex = null
        bstHighlightVal = null
        bstTraversalResult = ""
        operationStatus = "Reset complete. Ready for new operations."
      }
    )
  }
}

@Composable
fun BstNodeBadge(
  value: Int,
  isHighlighted: Boolean,
  modifier: Modifier = Modifier
) {
  Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
      .size(34.dp)
      .clip(CircleShape)
      .background(if (isHighlighted) AccentGreen else MidnightBlue)
      .border(1.5.dp, if (isHighlighted) Color.White else NeonCyan, CircleShape)
  ) {
    Text(
      text = value.toString(),
      style = CodeTextStyle.copy(fontWeight = FontWeight.Bold, color = Color.White, fontSize = 11.sp)
    )
  }
}
