package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Subject
import com.example.model.TestDuration
import com.example.ui.theme.ApexEmerald
import com.example.ui.theme.ApexGold
import com.example.ui.theme.ApexPrimary
import com.example.ui.theme.ApexPrimaryLight
import com.example.ui.viewmodel.ApexUiState

@Composable
fun DashboardScreen(
  state: ApexUiState,
  onStartTest: (TestDuration, Subject?) -> Unit,
  onOpenPrizeVault: () -> Unit,
  onOpenDiagnostics: () -> Unit,
  onOpenAccount: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  var selectedDuration by remember { mutableStateOf(TestDuration.TEN_MINUTES) }
  var selectedSubject by remember { mutableStateOf<Subject?>(null) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    // Top Profile & Stat Banner
    item {
      HeaderStatsCard(
        studentName = state.studentName,
        streakDays = state.streakDays,
        sparks = state.totalSparks,
        testsCompleted = state.testsCompleted,
        onOpenAccount = onOpenAccount
      )
    }

    // Recommendation based on past weakness
    item {
      val weakTopic = state.latestReport?.weakChapters?.firstOrNull() ?: "Trigonometry & Chemical Reactions"
      WeakTopicRecommendationCard(
        weakTopic = weakTopic,
        onStartTargetedTest = {
          selectedSubject = state.latestReport?.subject ?: Subject.MATHEMATICS
          onStartTest(TestDuration.TEN_MINUTES, selectedSubject)
        }
      )
    }

    // Test Configuration Section
    item {
      Text(
        text = "Start Diagnostic Test",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
      )
      Text(
        text = "Timed syllabus drills to identify gaps and earn study unlocks",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    // Duration Selector (10 vs 20 min)
    item {
      Text(
        text = "Select Test Duration",
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface
      )
      Spacer(modifier = Modifier.height(8.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        DurationOptionCard(
          duration = TestDuration.TEN_MINUTES,
          isSelected = selectedDuration == TestDuration.TEN_MINUTES,
          onClick = { selectedDuration = TestDuration.TEN_MINUTES },
          modifier = Modifier.weight(1f)
        )
        DurationOptionCard(
          duration = TestDuration.TWENTY_MINUTES,
          isSelected = selectedDuration == TestDuration.TWENTY_MINUTES,
          onClick = { selectedDuration = TestDuration.TWENTY_MINUTES },
          modifier = Modifier.weight(1f)
        )
      }
    }

    // Subject Filter Chips
    item {
      Text(
        text = "Target Subject (or All Core Subjects)",
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface
      )
      Spacer(modifier = Modifier.height(8.dp))
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        item {
          FilterChip(
            selected = selectedSubject == null,
            onClick = { selectedSubject = null },
            label = { Text("All Subjects (Board Mix)") },
            leadingIcon = {
              if (selectedSubject == null) {
                Icon(
                  Icons.Default.CheckCircle,
                  contentDescription = null,
                  modifier = Modifier.size(16.dp)
                )
              }
            },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
              selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            modifier = Modifier.testTag("filter_all_subjects")
          )
        }
        items(Subject.values()) { subject ->
          FilterChip(
            selected = selectedSubject == subject,
            onClick = { selectedSubject = subject },
            label = { Text(subject.displayName) },
            leadingIcon = {
              if (selectedSubject == subject) {
                Icon(
                  Icons.Default.CheckCircle,
                  contentDescription = null,
                  modifier = Modifier.size(16.dp)
                )
              }
            },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
              selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            modifier = Modifier.testTag("filter_${subject.code.lowercase()}")
          )
        }
      }
    }

    // Launch Test Action Button
    item {
      Button(
        onClick = { onStartTest(selectedDuration, selectedSubject) },
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .testTag("start_test_button"),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = ApexPrimaryLight,
          contentColor = Color.White
        )
      ) {
        Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Begin ${selectedDuration.label} (${if (selectedSubject != null) selectedSubject!!.displayName else "Full Syllabus"})",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold
        )
      }
    }

    // Quick Hub: Knowledge Vault & Diagnostic Report shortcuts
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Card(
          onClick = onOpenPrizeVault,
          modifier = Modifier
            .weight(1f)
            .testTag("dashboard_vault_card"),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
          )
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Icon(
              Icons.Default.AutoAwesome,
              contentDescription = null,
              tint = ApexGold,
              modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Knowledge Vault",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "Formula sheets & cheat aids",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        Card(
          onClick = onOpenDiagnostics,
          modifier = Modifier
            .weight(1f)
            .testTag("dashboard_diagnostics_card"),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
          )
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Icon(
              Icons.Default.School,
              contentDescription = null,
              tint = ApexEmerald,
              modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "AI Diagnostics",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "Personalized revision tips",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }
    }
  }
}

@Composable
fun HeaderStatsCard(
  studentName: String,
  streakDays: Int,
  sparks: Int,
  testsCompleted: Int,
  onOpenAccount: () -> Unit = {}
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(24.dp))
      .background(
        Brush.horizontalGradient(
          colors = listOf(ApexPrimary, Color(0xFF1D4ED8))
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
        Column(
          modifier = Modifier
            .weight(1f)
            .clickable { onOpenAccount() }
        ) {
          Text(
            text = "Welcome, $studentName",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
          )
          Text(
            text = "CBSE Class 10 Board Accelerator • Tap to view Account",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFBFDBFE)
          )
        }
        Surface(
          shape = CircleShape,
          color = Color.White.copy(alpha = 0.15f),
          modifier = Modifier
            .size(44.dp)
            .clickable { onOpenAccount() }
            .testTag("dashboard_profile_avatar_btn")
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(Icons.Default.EmojiEvents, contentDescription = "Account Settings", tint = ApexGold)
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Metric Badges Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        StatBadge(
          icon = Icons.Default.LocalFireDepartment,
          tint = ApexGold,
          value = "$streakDays Days",
          label = "Study Streak"
        )
        StatBadge(
          icon = Icons.Default.ElectricBolt,
          tint = ApexGold,
          value = "$sparks",
          label = "Mastery Sparks"
        )
        StatBadge(
          icon = Icons.Default.Schedule,
          tint = Color(0xFF93C5FD),
          value = "$testsCompleted",
          label = "Drills Done"
        )
      }
    }
  }
}

@Composable
fun StatBadge(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  tint: Color,
  value: String,
  label: String
) {
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = Color.White.copy(alpha = 0.12f),
    modifier = Modifier.padding(horizontal = 2.dp)
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(20.dp))
      Spacer(modifier = Modifier.width(6.dp))
      Column {
        Text(
          text = value,
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.Bold,
          color = Color.White
        )
        Text(
          text = label,
          style = MaterialTheme.typography.labelSmall,
          color = Color(0xFFE2E8F0),
          fontSize = 10.sp
        )
      }
    }
  }
}

@Composable
fun DurationOptionCard(
  duration: TestDuration,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .clickable { onClick() }
      .border(
        width = if (isSelected) 2.dp else 1.dp,
        color = if (isSelected) ApexPrimaryLight else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
        shape = RoundedCornerShape(16.dp)
      ),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
      else MaterialTheme.colorScheme.surface
    )
  ) {
    Column(
      modifier = Modifier.padding(16.dp),
      horizontalAlignment = Alignment.Start
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = "${duration.minutes} Min",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
        if (isSelected) {
          Icon(
            Icons.Default.CheckCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(18.dp)
          )
        }
      }
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = "${duration.questionCount} Questions",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}

@Composable
fun WeakTopicRecommendationCard(
  weakTopic: String,
  onStartTargetedTest: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = Color(0xFFFEF3C7) // warm amber
    ),
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier.padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(42.dp)
          .clip(CircleShape)
          .background(ApexGold),
        contentAlignment = Alignment.Center
      ) {
        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White)
      }
      Spacer(modifier = Modifier.width(12.dp))
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = "PRIORITY DIAGNOSTIC GAP",
          style = MaterialTheme.typography.labelSmall,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF92400E)
        )
        Text(
          text = weakTopic,
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF78350F)
        )
      }
      Button(
        onClick = onStartTargetedTest,
        colors = ButtonDefaults.buttonColors(
          containerColor = Color(0xFF78350F),
          contentColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
      ) {
        Text("Practice", style = MaterialTheme.typography.labelMedium)
      }
    }
  }
}
