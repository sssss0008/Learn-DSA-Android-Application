package com.example.data.model

enum class DifficultyLevel(val label: String) {
  BEGINNER("Beginner"),
  INTERMEDIATE("Intermediate"),
  ADVANCED("Advanced"),
  INTERVIEW("Interview"),
  COMPETITIVE("Competitive")
}

data class CodingChallenge(
  val id: String,
  val title: String,
  val difficulty: DifficultyLevel,
  val topic: String,
  val estimatedMinutes: Int,
  val points: Int,
  val problemStatement: String,
  val examples: List<Pair<String, String>>, // Input, Output
  val constraints: List<String>,
  val hint: String,
  val initialCode: Map<ProgrammingLanguage, String>,
  val solutionCode: Map<ProgrammingLanguage, String>,
  val testCases: List<Pair<String, String>>,
  val complexityExplanation: String
)

data class DebuggingProblem(
  val id: String,
  val title: String,
  val difficulty: DifficultyLevel,
  val structure: String,
  val bugDescription: String,
  val buggyCode: String,
  val fixedCode: String,
  val explanation: String
)

data class QuizQuestion(
  val id: String,
  val question: String,
  val options: List<String>,
  val correctIndex: Int,
  val explanation: String,
  val isVisual: Boolean = false,
  val visualHint: String = ""
)

data class MiniProject(
  val id: String,
  val title: String,
  val subtitle: String,
  val primaryDs: String,
  val difficulty: DifficultyLevel,
  val description: String,
  val architecture: String,
  val keyOperations: List<String>,
  val interactiveSimulationHint: String
)

data class Flashcard(
  val id: String,
  val category: String,
  val front: String,
  val back: String,
  val codeExample: String = ""
)

data class GlossaryTerm(
  val term: String,
  val category: String,
  val definition: String,
  val formulaOrNotation: String,
  val visualExample: String
)
