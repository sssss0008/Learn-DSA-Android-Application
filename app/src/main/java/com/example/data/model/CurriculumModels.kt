package com.example.data.model

enum class ProgrammingLanguage(val displayName: String, val extension: String) {
  PYTHON("Python", "py"),
  JAVA("Java", "java"),
  CPP("C++", "cpp"),
  KOTLIN("Kotlin", "kt"),
  JAVASCRIPT("JavaScript", "js"),
  CSHARP("C#", "cs"),
  GO("Go", "go"),
  RUST("Rust", "rs")
}

data class CodeSnippet(
  val language: ProgrammingLanguage,
  val code: String,
  val explanation: String
)

data class Lesson(
  val id: String,
  val moduleId: Int,
  val title: String,
  val summary: String,
  val concept: String,
  val visualDescription: String,
  val codeSnippets: Map<ProgrammingLanguage, String>,
  val timeComplexity: String,
  val spaceComplexity: String,
  val realWorldApplications: List<String>,
  val commonMistakes: List<String>,
  val edgeCases: List<String>,
  val interviewTips: String
)

data class CurriculumModule(
  val id: Int,
  val level: Int,
  val title: String,
  val iconName: String,
  val description: String,
  val lessonsCount: Int,
  val estimatedMinutes: Int,
  val isAdvanced: Boolean = false,
  val lessons: List<Lesson> = emptyList()
)

data class ComplexityInfo(
  val structureName: String,
  val searchTime: String,
  val insertTime: String,
  val deleteTime: String,
  val accessTime: String,
  val spaceComplexity: String,
  val notes: String
)
