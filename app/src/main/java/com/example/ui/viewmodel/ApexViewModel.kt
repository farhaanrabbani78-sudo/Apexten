package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.GeminiStudyAdvisor
import com.example.data.Class10QuestionBank
import com.example.data.PrizeCatalog
import com.example.data.StudentProfileManager
import com.example.model.CognitiveSkill
import com.example.model.LeaderboardEntry
import com.example.model.Question
import com.example.model.StudyPrize
import com.example.model.Subject
import com.example.model.TestDuration
import com.example.model.UserTestResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

enum class NavigationTab {
  DASHBOARD,
  DIAGNOSTICS,
  PRIZES,
  LEADERBOARD,
  ACCOUNT
}

data class ActiveTestState(
  val isActive: Boolean = false,
  val questions: List<Question> = emptyList(),
  val duration: TestDuration = TestDuration.TEN_MINUTES,
  val subject: Subject? = null,
  val currentQuestionIndex: Int = 0,
  val userAnswers: Map<Int, Int> = emptyMap(), // index -> chosen option
  val flaggedIndices: Set<Int> = emptySet(),
  val remainingSeconds: Int = 600,
  val isSubmitting: Boolean = false
)

data class ApexUiState(
  val isLoggedIn: Boolean = false,
  val currentTab: NavigationTab = NavigationTab.DASHBOARD,
  val activeTest: ActiveTestState = ActiveTestState(),
  val latestReport: UserTestResult? = null,
  val isAiAdviceLoading: Boolean = false,
  val prizes: List<StudyPrize> = emptyList(),
  val selectedPrizeForStudy: StudyPrize? = null,
  val leaderboard: List<LeaderboardEntry> = emptyList(),
  val totalSparks: Int = 0,
  val streakDays: Int = 0,
  val testsCompleted: Int = 0,
  val studentName: String = "",
  val studentEmail: String = "",
  val studentPhone: String = "",
  val authProvider: String = "Google",
  val schoolName: String = "Delhi Public School",
  val boardName: String = "CBSE Class 10",
  val targetGoal: String = "Aiming for 95%+ in Boards",
  val dailyReminder: Boolean = true,
  val soundHaptics: Boolean = true,
  val testHistory: List<UserTestResult> = emptyList(),
  val snackbarMessage: String? = null
)

class ApexViewModel(application: Application) : AndroidViewModel(application) {
  private val profileManager = StudentProfileManager(application)

  private val _uiState = MutableStateFlow(ApexUiState())
  val uiState: StateFlow<ApexUiState> = _uiState.asStateFlow()

  private var timerJob: Job? = null

  init {
    refreshProfileAndPrizes()
  }

  fun refreshProfileAndPrizes() {
    val unlockedIds = profileManager.getUnlockedPrizeIds()
    val updatedPrizes = PrizeCatalog.defaultPrizes.map { prize ->
      prize.copy(isUnlocked = unlockedIds.contains(prize.id))
    }

    _uiState.update { current ->
      current.copy(
        isLoggedIn = profileManager.isLoggedIn,
        totalSparks = profileManager.totalSparks,
        streakDays = profileManager.streakDays,
        testsCompleted = profileManager.testsCompleted,
        studentName = profileManager.studentName,
        studentEmail = profileManager.studentEmail,
        studentPhone = profileManager.studentPhone,
        authProvider = profileManager.authProvider,
        schoolName = profileManager.schoolName,
        boardName = profileManager.boardName,
        targetGoal = profileManager.targetGoal,
        dailyReminder = profileManager.dailyReminderEnabled,
        soundHaptics = profileManager.soundHapticsEnabled,
        prizes = updatedPrizes,
        leaderboard = profileManager.getLeaderboard(),
        testHistory = profileManager.getTestHistory(),
        latestReport = current.latestReport ?: profileManager.getTestHistory().firstOrNull()
      )
    }
  }

  fun loginWithGoogle(name: String = "Farhaan Rabbani", email: String = "farhaanrabbani78@gmail.com") {
    profileManager.performLogin(
      name = name,
      email = email,
      phone = profileManager.studentPhone,
      provider = "Google"
    )
    refreshProfileAndPrizes()
    _uiState.update {
      it.copy(
        currentTab = NavigationTab.DASHBOARD,
        snackbarMessage = "🎉 Welcome, $name! Signed in via Google."
      )
    }
  }

  fun loginWithFacebook(name: String = "Farhaan Rabbani", email: String = "farhaan.rabbani@facebook.com") {
    profileManager.performLogin(
      name = name,
      email = email,
      phone = profileManager.studentPhone,
      provider = "Facebook"
    )
    refreshProfileAndPrizes()
    _uiState.update {
      it.copy(
        currentTab = NavigationTab.DASHBOARD,
        snackbarMessage = "🎉 Welcome, $name! Signed in via Facebook."
      )
    }
  }

  fun loginWithPhone(phone: String, name: String = "Student") {
    val finalName = if (name.isNotBlank()) name else "Farhaan Rabbani"
    profileManager.performLogin(
      name = finalName,
      email = "$phone@mobile.student.in",
      phone = phone,
      provider = "Mobile Number"
    )
    refreshProfileAndPrizes()
    _uiState.update {
      it.copy(
        currentTab = NavigationTab.DASHBOARD,
        snackbarMessage = "🎉 Verified! Welcome, $finalName."
      )
    }
  }

  fun updateProfile(
    name: String,
    email: String,
    phone: String,
    school: String,
    board: String,
    goal: String
  ) {
    profileManager.updateProfileDetails(name, email, phone, school, board, goal)
    refreshProfileAndPrizes()
    _uiState.update {
      it.copy(snackbarMessage = "Profile updated successfully! Name: $name")
    }
  }

  fun updatePreferences(reminder: Boolean, sound: Boolean) {
    profileManager.updatePreferences(reminder, sound)
    refreshProfileAndPrizes()
    _uiState.update {
      it.copy(snackbarMessage = "Settings saved.")
    }
  }

  fun logout() {
    profileManager.performLogout()
    refreshProfileAndPrizes()
    _uiState.update {
      it.copy(
        currentTab = NavigationTab.DASHBOARD,
        snackbarMessage = "You have been signed out."
      )
    }
  }

  fun switchTab(tab: NavigationTab) {
    _uiState.update { it.copy(currentTab = tab) }
  }

  fun clearSnackbar() {
    _uiState.update { it.copy(snackbarMessage = null) }
  }

  fun startTest(duration: TestDuration, subject: Subject?) {
    timerJob?.cancel()
    val questions = Class10QuestionBank.getQuestionsForTest(subject, duration.questionCount)
    val totalSeconds = duration.minutes * 60

    _uiState.update {
      it.copy(
        activeTest = ActiveTestState(
          isActive = true,
          questions = questions,
          duration = duration,
          subject = subject,
          currentQuestionIndex = 0,
          userAnswers = emptyMap(),
          flaggedIndices = emptySet(),
          remainingSeconds = totalSeconds,
          isSubmitting = false
        )
      )
    }

    // Launch countdown timer
    timerJob = viewModelScope.launch {
      while (true) {
        delay(1000)
        val currentTest = _uiState.value.activeTest
        if (!currentTest.isActive) break

        if (currentTest.remainingSeconds <= 1) {
          submitTest()
          break
        } else {
          _uiState.update {
            it.copy(
              activeTest = it.activeTest.copy(
                remainingSeconds = it.activeTest.remainingSeconds - 1
              )
            )
          }
        }
      }
    }
  }

  fun selectAnswer(questionIndex: Int, optionIndex: Int) {
    _uiState.update {
      val newAnswers = it.activeTest.userAnswers.toMutableMap()
      newAnswers[questionIndex] = optionIndex
      it.copy(activeTest = it.activeTest.copy(userAnswers = newAnswers))
    }
  }

  fun toggleFlag(questionIndex: Int) {
    _uiState.update {
      val flagged = it.activeTest.flaggedIndices.toMutableSet()
      if (flagged.contains(questionIndex)) {
        flagged.remove(questionIndex)
      } else {
        flagged.add(questionIndex)
      }
      it.copy(activeTest = it.activeTest.copy(flaggedIndices = flagged))
    }
  }

  fun navigateQuestion(newIndex: Int) {
    val total = _uiState.value.activeTest.questions.size
    if (newIndex in 0 until total) {
      _uiState.update {
        it.copy(activeTest = it.activeTest.copy(currentQuestionIndex = newIndex))
      }
    }
  }

  fun cancelTest() {
    timerJob?.cancel()
    _uiState.update {
      it.copy(activeTest = ActiveTestState(isActive = false))
    }
  }

  fun submitTest() {
    timerJob?.cancel()
    val testState = _uiState.value.activeTest
    if (!testState.isActive) return

    val questions = testState.questions
    val answers = testState.userAnswers
    val totalQuestions = questions.size
    val totalSecondsAllocated = testState.duration.minutes * 60
    val timeSpent = (totalSecondsAllocated - testState.remainingSeconds).coerceAtLeast(1)

    var correctCount = 0
    val chapterPerformance = mutableMapOf<String, Pair<Int, Int>>() // chapter -> (correct, total)
    val skillPerformance = mutableMapOf<CognitiveSkill, Pair<Int, Int>>()

    questions.forEachIndexed { index, q ->
      val isCorrect = answers[index] == q.correctIndex
      if (isCorrect) correctCount++

      // Chapter breakdown
      val currentChap = chapterPerformance.getOrDefault(q.chapter, 0 to 0)
      chapterPerformance[q.chapter] = (currentChap.first + (if (isCorrect) 1 else 0)) to (currentChap.second + 1)

      // Skill breakdown
      val currentSkill = skillPerformance.getOrDefault(q.cognitiveSkill, 0 to 0)
      skillPerformance[q.cognitiveSkill] = (currentSkill.first + (if (isCorrect) 1 else 0)) to (currentSkill.second + 1)
    }

    val accuracyPct = if (totalQuestions > 0) (correctCount * 100) / totalQuestions else 0
    // Sparks calculation: base completion + accuracy multiplier
    val sparksEarned = (accuracyPct / 2) + (if (accuracyPct >= 80) 25 else 10)

    val weakChapters = chapterPerformance
      .filter { (it.value.first.toDouble() / it.value.second) < 0.6 }
      .keys.toList()

    val strongChapters = chapterPerformance
      .filter { (it.value.first.toDouble() / it.value.second) >= 0.7 }
      .keys.toList()

    val skillBreakdown = skillPerformance.mapValues {
      (it.value.first.toDouble() / it.value.second.coerceAtLeast(1)) * 100.0
    }

    val preliminaryAdvice = GeminiStudyAdvisor.generateAlgorithmicAdvice(
      testState.subject,
      accuracyPct,
      timeSpent,
      weakChapters,
      strongChapters,
      skillBreakdown
    )

    val result = UserTestResult(
      id = UUID.randomUUID().toString(),
      timestamp = System.currentTimeMillis(),
      subject = testState.subject,
      durationMinutes = testState.duration.minutes,
      totalQuestions = totalQuestions,
      correctCount = correctCount,
      scorePercentage = accuracyPct,
      timeSpentSeconds = timeSpent,
      sparksEarned = sparksEarned,
      weakChapters = weakChapters,
      strongChapters = strongChapters,
      aiRecommendations = preliminaryAdvice
    )

    // Save profile update
    profileManager.recordTestCompletion(result)
    refreshProfileAndPrizes()

    _uiState.update {
      it.copy(
        activeTest = ActiveTestState(isActive = false),
        currentTab = NavigationTab.DIAGNOSTICS,
        latestReport = result,
        isAiAdviceLoading = true
      )
    }

    // Attempt online Gemini personalization
    viewModelScope.launch {
      val geminiAdvice = GeminiStudyAdvisor.generatePersonalizedAdvice(
        testState.subject,
        accuracyPct,
        timeSpent,
        weakChapters,
        strongChapters,
        skillBreakdown
      )

      val updatedResult = result.copy(aiRecommendations = geminiAdvice)
      _uiState.update { current ->
        current.copy(
          latestReport = updatedResult,
          isAiAdviceLoading = false
        )
      }
    }
  }

  fun unlockPrize(prize: StudyPrize) {
    if (prize.isUnlocked) {
      viewPrize(prize)
      return
    }

    val success = profileManager.unlockPrize(prize.id, prize.sparkCost)
    if (success) {
      refreshProfileAndPrizes()
      _uiState.update {
        it.copy(
          snackbarMessage = "🎉 Unlocked ${prize.title}! Added to your study arsenal.",
          selectedPrizeForStudy = prize.copy(isUnlocked = true)
        )
      }
    } else {
      val needed = prize.sparkCost - profileManager.totalSparks
      _uiState.update {
        it.copy(snackbarMessage = "Need $needed more Sparks! Complete a 10-20 min test to earn Sparks.")
      }
    }
  }

  fun viewPrize(prize: StudyPrize) {
    _uiState.update { it.copy(selectedPrizeForStudy = prize) }
  }

  fun closePrizeViewer() {
    _uiState.update { it.copy(selectedPrizeForStudy = null) }
  }
}
