package com.example.model

enum class Subject(val displayName: String, val code: String) {
  MATHEMATICS("Mathematics", "MATH"),
  SCIENCE("Science", "SCI"),
  SOCIAL_SCIENCE("Social Science", "SST"),
  ENGLISH("English", "ENG")
}

enum class CognitiveSkill(val label: String, val tip: String) {
  CONCEPTUAL_CLARITY("Concept Clarity", "Revisit foundational definitions and core principles."),
  FORMULA_APPLICATION("Formula & Application", "Practice step-by-step substitution into standard formulas."),
  LOGICAL_REASONING("Reasoning & Deduction", "Eliminate extreme distractors and trace causality."),
  RECALL_TERMINOLOGY("Key Terminology", "Review keywords, historical dates, and definitions.")
}

data class Question(
  val id: String,
  val subject: Subject,
  val chapter: String,
  val questionText: String,
  val options: List<String>,
  val correctIndex: Int,
  val explanation: String,
  val cognitiveSkill: CognitiveSkill,
  val difficulty: String = "Medium"
)

enum class PrizeType(val label: String) {
  FORMULA_SHEET("Formula Sheet"),
  MIND_MAP("Visual Mind Map"),
  EXAM_CHEATSHEET("Board Exam Cheat Sheet"),
  CONCEPT_CARDS("Mastery Flashcard Pack")
}

data class StudyPrize(
  val id: String,
  val title: String,
  val subject: Subject,
  val type: PrizeType,
  val sparkCost: Int,
  val isUnlocked: Boolean = false,
  val description: String,
  val keyConcepts: List<String>,
  val detailedStudyNotes: String
)

enum class TestDuration(val minutes: Int, val questionCount: Int, val label: String) {
  TEN_MINUTES(10, 10, "10-Min Sprint"),
  TWENTY_MINUTES(20, 20, "20-Min Board Mock")
}

data class UserTestResult(
  val id: String,
  val timestamp: Long,
  val subject: Subject?,
  val durationMinutes: Int,
  val totalQuestions: Int,
  val correctCount: Int,
  val scorePercentage: Int,
  val timeSpentSeconds: Int,
  val sparksEarned: Int,
  val weakChapters: List<String>,
  val strongChapters: List<String>,
  val aiRecommendations: String
)

data class LeaderboardEntry(
  val rank: Int,
  val name: String,
  val avatarInitial: String,
  val avatarColorHex: Long,
  val sparks: Int,
  val testsCount: Int,
  val streakDays: Int,
  val tier: String,
  val isCurrentUser: Boolean = false
)
