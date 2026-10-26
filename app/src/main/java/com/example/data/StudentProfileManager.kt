package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.LeaderboardEntry
import com.example.model.Subject
import com.example.model.UserTestResult
import org.json.JSONArray
import org.json.JSONObject

class StudentProfileManager(context: Context) {
  private val prefs: SharedPreferences =
    context.getSharedPreferences("apexten_student_profile", Context.MODE_PRIVATE)

  var isLoggedIn: Boolean
    get() = prefs.getBoolean("is_logged_in", false)
    set(value) = prefs.edit().putBoolean("is_logged_in", value).apply()

  var authProvider: String
    get() = prefs.getString("auth_provider", "Google") ?: "Google"
    set(value) = prefs.edit().putString("auth_provider", value).apply()

  var studentName: String
    get() = prefs.getString("student_name", "Aryan Sharma") ?: "Aryan Sharma"
    set(value) = prefs.edit().putString("student_name", value).apply()

  var studentEmail: String
    get() = prefs.getString("student_email", "student@apexclass10.edu") ?: "student@apexclass10.edu"
    set(value) = prefs.edit().putString("student_email", value).apply()

  var studentPhone: String
    get() = prefs.getString("student_phone", "+91 98765 43210") ?: "+91 98765 43210"
    set(value) = prefs.edit().putString("student_phone", value).apply()

  var schoolName: String
    get() = prefs.getString("school_name", "Delhi Public School") ?: "Delhi Public School"
    set(value) = prefs.edit().putString("school_name", value).apply()

  var boardName: String
    get() = prefs.getString("board_name", "CBSE Class 10") ?: "CBSE Class 10"
    set(value) = prefs.edit().putString("board_name", value).apply()

  var targetGoal: String
    get() = prefs.getString("target_goal", "Aiming for 95%+ in Boards") ?: "Aiming for 95%+ in Boards"
    set(value) = prefs.edit().putString("target_goal", value).apply()

  var dailyReminderEnabled: Boolean
    get() = prefs.getBoolean("daily_reminder", true)
    set(value) = prefs.edit().putBoolean("daily_reminder", value).apply()

  var soundHapticsEnabled: Boolean
    get() = prefs.getBoolean("sound_haptics", true)
    set(value) = prefs.edit().putBoolean("sound_haptics", value).apply()

  var totalSparks: Int
    get() = prefs.getInt("total_sparks", 75) // initial starter bonus so they feel rewarded
    set(value) = prefs.edit().putInt("total_sparks", value).apply()

  var streakDays: Int
    get() = prefs.getInt("streak_days", 4)
    set(value) = prefs.edit().putInt("streak_days", value).apply()

  var testsCompleted: Int
    get() = prefs.getInt("tests_completed", 3)
    set(value) = prefs.edit().putInt("tests_completed", value).apply()

  fun performLogin(name: String, email: String, phone: String, provider: String) {
    prefs.edit()
      .putBoolean("is_logged_in", true)
      .putString("student_name", name.ifBlank { "Student" })
      .putString("student_email", email)
      .putString("student_phone", phone)
      .putString("auth_provider", provider)
      .apply()
  }

  fun performLogout() {
    prefs.edit().putBoolean("is_logged_in", false).apply()
  }

  fun updateProfileDetails(name: String, email: String, phone: String, school: String, board: String, goal: String) {
    prefs.edit()
      .putString("student_name", name.ifBlank { studentName })
      .putString("student_email", email)
      .putString("student_phone", phone)
      .putString("school_name", school)
      .putString("board_name", board)
      .putString("target_goal", goal)
      .apply()
  }

  fun updatePreferences(reminder: Boolean, sound: Boolean) {
    prefs.edit()
      .putBoolean("daily_reminder", reminder)
      .putBoolean("sound_haptics", sound)
      .apply()
  }

  fun getUnlockedPrizeIds(): Set<String> {
    val defaultSet = setOf("prize_trig_sheet")
    return prefs.getStringSet("unlocked_prize_ids", defaultSet)?.toSet() ?: defaultSet
  }

  fun unlockPrize(prizeId: String, cost: Int): Boolean {
    if (totalSparks < cost) return false
    val currentSet = getUnlockedPrizeIds().toMutableSet()
    currentSet.add(prizeId)
    prefs.edit()
      .putStringSet("unlocked_prize_ids", currentSet)
      .putInt("total_sparks", totalSparks - cost)
      .apply()
    return true
  }

  fun recordTestCompletion(result: UserTestResult) {
    totalSparks += result.sparksEarned
    testsCompleted += 1
    // Update test history
    val history = getTestHistory().toMutableList()
    history.add(0, result) // most recent first
    saveTestHistory(history.take(20))
  }

  fun getTestHistory(): List<UserTestResult> {
    val jsonString = prefs.getString("test_history_json", null) ?: return getSampleHistory()
    return try {
      val array = JSONArray(jsonString)
      val list = mutableListOf<UserTestResult>()
      for (i in 0 until array.length()) {
        val obj = array.getJSONObject(i)
        list.add(
          UserTestResult(
            id = obj.getString("id"),
            timestamp = obj.getLong("timestamp"),
            subject = if (obj.has("subject") && !obj.isNull("subject")) {
              Subject.valueOf(obj.getString("subject"))
            } else null,
            durationMinutes = obj.getInt("durationMinutes"),
            totalQuestions = obj.getInt("totalQuestions"),
            correctCount = obj.getInt("correctCount"),
            scorePercentage = obj.getInt("scorePercentage"),
            timeSpentSeconds = obj.getInt("timeSpentSeconds"),
            sparksEarned = obj.getInt("sparksEarned"),
            weakChapters = parseStringList(obj.getJSONArray("weakChapters")),
            strongChapters = parseStringList(obj.getJSONArray("strongChapters")),
            aiRecommendations = obj.optString("aiRecommendations", "")
          )
        )
      }
      list
    } catch (e: Exception) {
      getSampleHistory()
    }
  }

  private fun saveTestHistory(history: List<UserTestResult>) {
    val array = JSONArray()
    for (item in history) {
      val obj = JSONObject()
      obj.put("id", item.id)
      obj.put("timestamp", item.timestamp)
      obj.put("subject", item.subject?.name)
      obj.put("durationMinutes", item.durationMinutes)
      obj.put("totalQuestions", item.totalQuestions)
      obj.put("correctCount", item.correctCount)
      obj.put("scorePercentage", item.scorePercentage)
      obj.put("timeSpentSeconds", item.timeSpentSeconds)
      obj.put("sparksEarned", item.sparksEarned)
      obj.put("weakChapters", JSONArray(item.weakChapters))
      obj.put("strongChapters", JSONArray(item.strongChapters))
      obj.put("aiRecommendations", item.aiRecommendations)
      array.put(obj)
    }
    prefs.edit().putString("test_history_json", array.toString()).apply()
  }

  private fun parseStringList(array: JSONArray): List<String> {
    val result = mutableListOf<String>()
    for (i in 0 until array.length()) {
      result.add(array.getString(i))
    }
    return result
  }

  private fun getSampleHistory(): List<UserTestResult> {
    return listOf(
      UserTestResult(
        id = "sample_test_1",
        timestamp = System.currentTimeMillis() - 86400000L,
        subject = Subject.SCIENCE,
        durationMinutes = 10,
        totalQuestions = 10,
        correctCount = 8,
        scorePercentage = 80,
        timeSpentSeconds = 480,
        sparksEarned = 40,
        weakChapters = listOf("Light - Reflection & Refraction"),
        strongChapters = listOf("Acids, Bases & Salts", "Life Processes"),
        aiRecommendations = "Review concave mirror sign convention formula: 1/f = 1/v + 1/u. Practice 3 ray diagram problems."
      ),
      UserTestResult(
        id = "sample_test_2",
        timestamp = System.currentTimeMillis() - 172800000L,
        subject = Subject.MATHEMATICS,
        durationMinutes = 10,
        totalQuestions = 10,
        correctCount = 7,
        scorePercentage = 70,
        timeSpentSeconds = 540,
        sparksEarned = 35,
        weakChapters = listOf("Quadratic Equations"),
        strongChapters = listOf("Real Numbers", "Arithmetic Progressions"),
        aiRecommendations = "Reinforce Discriminant D = b² - 4ac rules for real vs imaginary roots."
      )
    )
  }

  fun getLeaderboard(): List<LeaderboardEntry> {
    val baseRanks = listOf(
      LeaderboardEntry(1, "Diya Patel", "D", 0xFF6366F1, 420, 16, 9, "Apex Scholar"),
      LeaderboardEntry(2, "Rohan Verma", "R", 0xFF0EA5E9, 385, 14, 7, "Diamond"),
      LeaderboardEntry(3, "Sneha Sen", "S", 0xFF10B981, 350, 13, 6, "Diamond"),
      LeaderboardEntry(4, "Aditya Rao", "A", 0xFF8B5CF6, 290, 11, 5, "Gold"),
      LeaderboardEntry(5, "Pooja Hegde", "P", 0xFFEC4899, 240, 9, 5, "Gold"),
      LeaderboardEntry(6, "Kunal Ghosh", "K", 0xFFF59E0B, 180, 7, 3, "Silver"),
      LeaderboardEntry(7, "Tanvi Shah", "T", 0xFF14B8A6, 120, 5, 2, "Silver")
    )

    val currentSparks = totalSparks
    val currentTests = testsCompleted
    val currentStreak = streakDays

    val currentUserEntry = LeaderboardEntry(
      rank = 0,
      name = "$studentName (You)",
      avatarInitial = studentName.take(1).uppercase(),
      avatarColorHex = 0xFF2563EB,
      sparks = currentSparks,
      testsCount = currentTests,
      streakDays = currentStreak,
      tier = when {
        currentSparks >= 400 -> "Apex Scholar"
        currentSparks >= 300 -> "Diamond"
        currentSparks >= 180 -> "Gold"
        else -> "Silver"
      },
      isCurrentUser = true
    )

    val combined = (baseRanks + currentUserEntry).sortedByDescending { it.sparks }
    return combined.mapIndexed { index, entry ->
      entry.copy(rank = index + 1)
    }
  }
}
