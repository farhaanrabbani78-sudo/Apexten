package com.example.ai

import com.example.BuildConfig
import com.example.model.CognitiveSkill
import com.example.model.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiStudyAdvisor {

  private val client = OkHttpClient.Builder()
    .connectTimeout(15, TimeUnit.SECONDS)
    .readTimeout(20, TimeUnit.SECONDS)
    .build()

  suspend fun generatePersonalizedAdvice(
    subject: Subject?,
    accuracyPercent: Int,
    timeSpentSeconds: Int,
    weakChapters: List<String>,
    strongChapters: List<String>,
    skillBreakdown: Map<CognitiveSkill, Double>
  ): String = withContext(Dispatchers.IO) {
    val apiKey = try {
      BuildConfig.GEMINI_API_KEY
    } catch (e: Throwable) {
      ""
    }

    if (apiKey.isNullOrBlank() || apiKey == "MY_GEMINI_API_KEY") {
      return@withContext generateAlgorithmicAdvice(
        subject,
        accuracyPercent,
        timeSpentSeconds,
        weakChapters,
        strongChapters,
        skillBreakdown
      )
    }

    try {
      val prompt = buildString {
        appendLine("You are an expert CBSE Class 10 academic mentor and diagnostic tutor.")
        appendLine("A student just finished a diagnostic test. Here is their performance diagnostic data:")
        appendLine("- Subject: ${subject?.displayName ?: "Comprehensive Mock"}")
        appendLine("- Overall Accuracy: $accuracyPercent%")
        appendLine("- Time Taken: ${timeSpentSeconds / 60}m ${timeSpentSeconds % 60}s")
        appendLine("- Mastered / Strong Topics: ${strongChapters.ifEmpty { listOf("None yet") }.joinToString()}")
        appendLine("- Problem / Weak Areas: ${weakChapters.ifEmpty { listOf("None! Perfect score") }.joinToString()}")
        appendLine("- Cognitive Skill Gaps:")
        skillBreakdown.forEach { (skill, pct) ->
          appendLine("  • ${skill.label}: ${pct.toInt()}% accuracy")
        }
        appendLine()
        appendLine("Write a personalized, motivating, and actionable 3-step study recommendation for this student.")
        appendLine("Format clearly with:")
        appendLine("1. 🎯 Diagnostic Diagnosis: Exactly why they lost marks in their weak topics.")
        appendLine("2. ⚡ High-Yield Revision Technique: A specific mnemonic, formula habit, or concept trick for Class 10.")
        appendLine("3. 📅 48-Hour Action Plan: Concrete practice steps and what prize/formula sheet to unlock in the Knowledge Vault.")
        appendLine("Keep tone encouraging, concise, direct, under 200 words, without generic filler.")
      }

      val requestJson = JSONObject().apply {
        val contentsArray = JSONArray().apply {
          val contentObj = JSONObject().apply {
            val partsArray = JSONArray().apply {
              put(JSONObject().apply { put("text", prompt) })
            }
            put("parts", partsArray)
          }
          put(contentObj)
        }
        put("contents", contentsArray)
      }

      val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"
      val request = Request.Builder()
        .url(url)
        .post(requestJson.toString().toRequestBody("application/json".toMediaType()))
        .build()

      val response = client.newCall(request).execute()
      val body = response.body?.string()

      if (response.isSuccessful && !body.isNullOrBlank()) {
        val responseObj = JSONObject(body)
        val candidates = responseObj.optJSONArray("candidates")
        if (candidates != null && candidates.length() > 0) {
          val candidate = candidates.getJSONObject(0)
          val content = candidate.optJSONObject("content")
          val parts = content?.optJSONArray("parts")
          if (parts != null && parts.length() > 0) {
            val text = parts.getJSONObject(0).optString("text")
            if (text.isNotBlank()) {
              return@withContext text.trim()
            }
          }
        }
      }
    } catch (e: Exception) {
      // Graceful fallback to algorithmic recommendation
    }

    return@withContext generateAlgorithmicAdvice(
      subject,
      accuracyPercent,
      timeSpentSeconds,
      weakChapters,
      strongChapters,
      skillBreakdown
    )
  }

  fun generateAlgorithmicAdvice(
    subject: Subject?,
    accuracyPercent: Int,
    timeSpentSeconds: Int,
    weakChapters: List<String>,
    strongChapters: List<String>,
    skillBreakdown: Map<CognitiveSkill, Double>
  ): String {
    val lowestSkill = skillBreakdown.minByOrNull { it.value }?.key ?: CognitiveSkill.FORMULA_APPLICATION
    val primaryWeakness = weakChapters.firstOrNull() ?: "General Practice"

    return buildString {
      append("🎯 DIAGNOSTIC DIAGNOSIS\n")
      if (accuracyPercent >= 80) {
        append("Excellent retention! You demonstrated strong conceptual clarity. Your primary margin of error came from tricky edge cases in $primaryWeakness.")
      } else if (accuracyPercent >= 50) {
        append("Solid foundation, but marks were dropped in $primaryWeakness due to ${lowestSkill.label.lowercase()}. You need targeted formula reinforcement rather than passive re-reading.")
      } else {
        append("Significant concept gaps detected in $primaryWeakness. You are rushing through questions—slow down and verify each calculation step.")
      }

      append("\n\n⚡ HIGH-YIELD REVISION TIP\n")
      when (lowestSkill) {
        CognitiveSkill.FORMULA_APPLICATION -> {
          append("Write down the given parameters and standard formula BEFORE substituting numbers. Use dimensional analysis and unit sanity checks.")
        }
        CognitiveSkill.CONCEPTUAL_CLARITY -> {
          append("Read NCERT exemplar explanations for $primaryWeakness. Summarize each sub-topic in 3 bullet points without looking at your book.")
        }
        CognitiveSkill.LOGICAL_REASONING -> {
          append("Use the Elimination Method: discard the two most extreme distractors first before committing to your final answer.")
        }
        CognitiveSkill.RECALL_TERMINOLOGY -> {
          append("Active recall flashcards are essential. Review chemical names, historical treaties, and grammar rules daily for 10 minutes.")
        }
      }

      append("\n\n📅 48-HOUR ACTION PLAN\n")
      append("1. Revisit $primaryWeakness notes and solve 5 previous-year board questions.\n")
      append("2. Unlock the corresponding study cheat sheet in the Knowledge Vault using your earned Sparks.\n")
      append("3. Take another 10-Minute Rapid Sprint tomorrow to verify mastery.")
    }
  }
}
