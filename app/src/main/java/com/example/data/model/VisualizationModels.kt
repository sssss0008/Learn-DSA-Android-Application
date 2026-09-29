package com.example.data.model

enum class VisualizerType(val title: String, val badge: String) {
  ARRAY("Array", "Linear"),
  LINKED_LIST("Singly Linked List", "Pointers"),
  STACK("Stack", "LIFO"),
  QUEUE("Queue", "FIFO"),
  BST("Binary Search Tree", "Hierarchical"),
  HASH_TABLE("Hash Table", "Direct Access"),
  GRAPH("Graph & BFS/DFS", "Network"),
  SORTING("Sorting Suite", "Algorithms"),
  BIG_O("Big-O Laboratory", "Complexity")
}

data class ArrayCell(
  val index: Int,
  val value: Int,
  val isHighlighted: Boolean = false,
  val isComparing: Boolean = false,
  val isTarget: Boolean = false,
  val memoryAddress: String = "0x${(1000 + index * 4).toString(16).uppercase()}"
)

data class ListNode(
  val id: Int,
  val value: Int,
  val nextId: Int? = null,
  val isHead: Boolean = false,
  val isHighlighted: Boolean = false,
  val memoryAddress: String = "0x${(4096 + id * 16).toString(16).uppercase()}"
)

data class TreeNode(
  val id: Int,
  val value: Int,
  val leftId: Int? = null,
  val rightId: Int? = null,
  val level: Int = 0,
  val isHighlighted: Boolean = false,
  val isComparing: Boolean = false
)

data class HashBucket(
  val bucketIndex: Int,
  val entries: List<Pair<String, Int>> = emptyList(),
  val isHighlighted: Boolean = false
)

data class GraphNode(
  val id: String,
  val label: String,
  val x: Float, // Normalized 0f to 1f
  val y: Float, // Normalized 0f to 1f
  val isVisited: Boolean = false,
  val isCurrent: Boolean = false,
  val distance: Int = Int.MAX_VALUE
)

data class GraphEdge(
  val fromId: String,
  val toId: String,
  val weight: Int = 1,
  val isTraversed: Boolean = false
)

data class VisualOperationStep(
  val stepNumber: Int,
  val description: String,
  val codeLine: String,
  val timeComplexity: String,
  val stateSnapshot: String = ""
)
