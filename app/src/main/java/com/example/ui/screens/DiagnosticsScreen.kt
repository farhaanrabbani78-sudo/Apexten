package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.UserTestResult
import com.example.ui.theme.ApexCoralRed
import com.example.ui.theme.ApexEmerald
import com.example.ui.theme.ApexGold
import com.example.ui.theme.ApexPrimary
import com.example.ui.theme.ApexPrimaryLight
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DiagnosticsScreen(
  latestReport: UserTestResult?,
  testHistory: List<UserTestResult>,
  isAiLoading: Boolean,
  onNavigateToPrizes: () -> Unit,
  onStartNewTest: () -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
    verticalArrangement = Arrangement.spacedBy(18.dp)
  ) {
    item {
      Text(
        text = "Performance Diagnostics",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.ExtraBold,
        color = MaterialTheme.colorScheme.onBackground
      )
      Text(
        text = "Detailed syllabus error diagnosis and personalized study strategy",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    if (latestReport == null) {
      item {
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(
              Icons.Default.Timer,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "No Tests Completed Yet",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Complete your first 10 or 20-minute diagnostic drill to generate deep study analytics.",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
              onClick = onStartNewTest,
              colors = ButtonDefaults.buttonColors(containerColor = ApexPrimaryLight),
              shape = RoundedCornerShape(12.dp)
            ) {
              Text("Take a 10-Min Test")
            }
          }
        }
      }
    } else {
      // Metric Card
      item {
        ScoreOverviewHeroCard(report = latestReport)
      }

      // Weak Chapters vs Mastered Chapters
      item {
        ChapterAnalysisCard(report = latestReport)
      }

      // Personalized AI Study Recommendations
      item {
        AiRecommendationsCard(
          recommendations = latestReport.aiRecommendations,
          isLoading = isAiLoading,
          onNavigateToPrizes = onNavigateToPrizes
        )
      }
    }

    // Past History Section
    if (testHistory.isNotEmpty()) {
      item {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(Icons.Default.History, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
          Text(
            text = "Test History & Score Growth",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
        }
      }

      items(testHistory) { test ->
        HistoryItemCard(test = test)
      }
    }
  }
}

@Composable
fun ScoreOverviewHeroCard(report: UserTestResult) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(24.dp))
      .background(
        Brush.linearGradient(
          colors = listOf(ApexPrimary, Color(0xFF1E40AF))
        )
      )
      .padding(20.dp)
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "LATEST TEST OUTCOME",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF93C5FD)
          )
          Text(
            text = report.subject?.displayName ?: "Comprehensive Board Mock",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
          )
        }
        Surface(
          shape = RoundedCornerShape(14.dp),
          color = ApexEmerald.copy(alpha = 0.25f),
          border = androidx.compose.foundation.BorderStroke(1.dp, ApexEmerald)
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.ElectricBolt, contentDescription = null, tint = ApexGold, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "+${report.sparksEarned} Sparks",
              fontWeight = FontWeight.Bold,
              color = Color.White,
              style = MaterialTheme.typography.labelMedium
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
      ) {
        MetricItem(
          label = "Accuracy",
          value = "${report.scorePercentage}%",
          subtext = "${report.correctCount}/${report.totalQuestions} Correct"
        )
        MetricItem(
          label = "Time Taken",
          value = "${report.timeSpentSeconds / 60}m ${report.timeSpentSeconds % 60}s",
          subtext = "of ${report.durationMinutes} min limit"
        )
        MetricItem(
          label = "Avg Speed",
          value = "${report.timeSpentSeconds / report.totalQuestions.coerceAtLeast(1)}s",
          subtext = "per question"
        )
      }
    }
  }
}

@Composable
fun MetricItem(label: String, value: String, subtext: String) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text(
      text = value,
      style = MaterialTheme.typography.headlineSmall,
      fontWeight = FontWeight.ExtraBold,
      color = Color.White
    )
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall,
      fontWeight = FontWeight.SemiBold,
      color = Color(0xFFBFDBFE)
    )
    Text(
      text = subtext,
      style = MaterialTheme.typography.labelSmall,
      color = Color(0xFFE2E8F0),
      fontSize = 10.sp
    )
  }
}

@Composable
fun ChapterAnalysisCard(report: UserTestResult) {
  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      Text(
        text = "Syllabus Mastery Breakdown",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )
      Spacer(modifier = Modifier.height(14.dp))

      // Weak areas
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.ErrorOutline, contentDescription = null, tint = ApexCoralRed, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Needs Immediate Revision:",
          style = MaterialTheme.typography.labelMedium,
          fontWeight = FontWeight.Bold,
          color = ApexCoralRed
        )
      }
      Spacer(modifier = Modifier.height(6.dp))
      if (report.weakChapters.isEmpty()) {
        Text(
          text = "🎉 No critical gaps! Perfect or near-perfect performance across all tested chapters.",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      } else {
        report.weakChapters.forEach { chapter ->
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFFEE2E2),
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(8.dp)
                  .clip(CircleShape)
                  .background(ApexCoralRed)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = chapter,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF991B1B)
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Strong areas
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = ApexEmerald, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Mastered Topics:",
          style = MaterialTheme.typography.labelMedium,
          fontWeight = FontWeight.Bold,
          color = ApexEmerald
        )
      }
      Spacer(modifier = Modifier.height(6.dp))
      if (report.strongChapters.isEmpty()) {
        Text(
          text = "Consistent practice needed to achieve topic mastery.",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      } else {
        report.strongChapters.forEach { chapter ->
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFD1FAE5),
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(8.dp)
                  .clip(CircleShape)
                  .background(ApexEmerald)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = chapter,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF065F46)
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun AiRecommendationsCard(
  recommendations: String,
  isLoading: Boolean,
  onNavigateToPrizes: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
    ),
    border = androidx.compose.foundation.BorderStroke(1.dp, ApexPrimaryLight.copy(alpha = 0.4f)),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(20.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(ApexPrimaryLight),
            contentAlignment = Alignment.Center
          ) {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "Personalized AI Study Advisor",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.primary
            )
            Text(
              text = "Tuned for Class 10 Board Exam prep",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        if (isLoading) {
          CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = recommendations,
        style = MaterialTheme.typography.bodyMedium,
        lineHeight = 22.sp,
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(16.dp))

      Button(
        onClick = onNavigateToPrizes,
        colors = ButtonDefaults.buttonColors(containerColor = ApexPrimaryLight),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("redeem_sparks_button")
      ) {
        Icon(Icons.Default.Lightbulb, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text("Unlock Recommended Cheat Sheet in Vault")
      }
    }
  }
}

@Composable
fun HistoryItemCard(test: UserTestResult) {
  val dateFormatted = remember(test.timestamp) {
    SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date(test.timestamp))
  }

  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier.padding(14.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = test.subject?.displayName ?: "All Core Subjects",
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "$dateFormatted • ${test.durationMinutes}-Min Drill",
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }

      Row(verticalAlignment = Alignment.CenterVertically) {
        Column(horizontalAlignment = Alignment.End) {
          Text(
            text = "${test.scorePercentage}%",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.ExtraBold,
            color = if (test.scorePercentage >= 70) ApexEmerald else ApexCoralRed
          )
          Text(
            text = "+${test.sparksEarned} ⚡",
            style = MaterialTheme.typography.labelSmall,
            color = ApexGold,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}
