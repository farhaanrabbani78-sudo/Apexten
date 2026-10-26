package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ApexCoralRed
import com.example.ui.theme.ApexEmerald
import com.example.ui.theme.ApexGold
import com.example.ui.theme.ApexPrimaryLight
import com.example.ui.viewmodel.ActiveTestState

@Composable
fun ActiveTestScreen(
  testState: ActiveTestState,
  onSelectAnswer: (Int, Int) -> Unit,
  onToggleFlag: (Int) -> Unit,
  onNavigateQuestion: (Int) -> Unit,
  onSubmitTest: () -> Unit,
  onCancelTest: () -> Unit,
  modifier: Modifier = Modifier
) {
  var showExitDialog by remember { mutableStateOf(false) }
  var showSubmitDialog by remember { mutableStateOf(false) }

  BackHandler {
    showExitDialog = true
  }

  val totalQuestions = testState.questions.size
  val currentIndex = testState.currentQuestionIndex
  val currentQuestion = testState.questions.getOrNull(currentIndex)
  val selectedAnswer = testState.userAnswers[currentIndex]
  val isFlagged = testState.flaggedIndices.contains(currentIndex)

  // Timer format MM:SS
  val minutes = testState.remainingSeconds / 60
  val seconds = testState.remainingSeconds % 60
  val timerText = String.format("%02d:%02d", minutes, seconds)
  val isUrgentTimer = testState.remainingSeconds <= 120

  val timerColor by animateColorAsState(
    targetValue = if (isUrgentTimer) ApexCoralRed else ApexPrimaryLight,
    label = "timerColor"
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    // Top Bar with Timer and Progress
    Surface(
      shadowElevation = 3.dp,
      color = MaterialTheme.colorScheme.surface
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(
            onClick = { showExitDialog = true },
            modifier = Modifier.testTag("exit_test_button")
          ) {
            Icon(Icons.Default.Close, contentDescription = "Exit Test")
          }

          // Countdown Timer Badge
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = timerColor.copy(alpha = 0.12f),
            border = androidx.compose.foundation.BorderStroke(1.dp, timerColor.copy(alpha = 0.5f))
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                Icons.Default.Schedule,
                contentDescription = null,
                tint = timerColor,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = timerText,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = timerColor
              )
            }
          }

          // Flag for review button
          IconButton(
            onClick = { onToggleFlag(currentIndex) },
            modifier = Modifier.testTag("flag_question_button")
          ) {
            Icon(
              imageVector = if (isFlagged) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
              contentDescription = "Flag for review",
              tint = if (isFlagged) ApexGold else MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Progress Indicator
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Question ${currentIndex + 1} of $totalQuestions",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
          )
          Text(
            text = "${testState.userAnswers.size} Answered",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
          progress = { if (totalQuestions > 0) (currentIndex + 1) / totalQuestions.toFloat() else 0f },
          modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(RoundedCornerShape(3.dp)),
          color = ApexPrimaryLight,
          trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
      }
    }

    // Scrollable Question Content
    if (currentQuestion != null) {
      Column(
        modifier = Modifier
          .weight(1f)
          .verticalScroll(rememberScrollState())
          .padding(16.dp)
      ) {
        // Topic Tags
        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.primaryContainer
          ) {
            Text(
              text = currentQuestion.chapter,
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.SemiBold,
              color = MaterialTheme.colorScheme.onPrimaryContainer,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }

          Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.secondaryContainer
          ) {
            Text(
              text = currentQuestion.cognitiveSkill.label,
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.SemiBold,
              color = MaterialTheme.colorScheme.onSecondaryContainer,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Question Statement
        Text(
          text = currentQuestion.questionText,
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.SemiBold,
          lineHeight = 24.sp,
          color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Multiple Choice Options
        currentQuestion.options.forEachIndexed { optIndex, optionText ->
          val isSelected = selectedAnswer == optIndex
          val optionLabel = ('A'.code + optIndex).toChar().toString()

          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 6.dp)
              .clip(RoundedCornerShape(14.dp))
              .clickable { onSelectAnswer(currentIndex, optIndex) }
              .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) ApexPrimaryLight else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                shape = RoundedCornerShape(14.dp)
              )
              .testTag("option_button_$optIndex"),
            colors = CardDefaults.cardColors(
              containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
              else MaterialTheme.colorScheme.surface
            )
          ) {
            Row(
              modifier = Modifier.padding(14.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(32.dp)
                  .clip(CircleShape)
                  .background(
                    if (isSelected) ApexPrimaryLight else MaterialTheme.colorScheme.surfaceVariant
                  ),
                contentAlignment = Alignment.Center
              ) {
                if (isSelected) {
                  Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                } else {
                  Text(
                    text = optionLabel,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelMedium
                  )
                }
              }

              Spacer(modifier = Modifier.width(14.dp))

              Text(
                text = optionText,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
              )
            }
          }
        }
      }
    }

    // Bottom Navigation Bar & Palette
    Surface(
      shadowElevation = 8.dp,
      color = MaterialTheme.colorScheme.surface
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        // Horizontal Question Dots Palette
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
        ) {
          itemsIndexed(testState.questions) { index, _ ->
            val isCurrent = index == currentIndex
            val isAnswered = testState.userAnswers.containsKey(index)
            val flagged = testState.flaggedIndices.contains(index)

            val bubbleBg = when {
              isCurrent -> ApexPrimaryLight
              flagged -> ApexGold
              isAnswered -> ApexEmerald
              else -> MaterialTheme.colorScheme.surfaceVariant
            }

            val textColor = when {
              isCurrent || flagged || isAnswered -> Color.White
              else -> MaterialTheme.colorScheme.onSurfaceVariant
            }

            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(bubbleBg)
                .clickable { onNavigateQuestion(index) }
                .border(
                  width = if (isCurrent) 2.dp else 0.dp,
                  color = if (isCurrent) MaterialTheme.colorScheme.primary else Color.Transparent,
                  shape = CircleShape
                )
                .testTag("palette_dot_$index"),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "${index + 1}",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = textColor
              )
            }
          }
        }

        // Action Buttons Row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          OutlinedButton(
            onClick = { onNavigateQuestion(currentIndex - 1) },
            enabled = currentIndex > 0,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .weight(1f)
              .testTag("prev_question_button")
          ) {
            Text("Previous")
          }

          if (currentIndex < totalQuestions - 1) {
            Button(
              onClick = { onNavigateQuestion(currentIndex + 1) },
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier
                .weight(1f)
                .testTag("next_question_button"),
              colors = ButtonDefaults.buttonColors(containerColor = ApexPrimaryLight)
            ) {
              Text("Next")
            }
          } else {
            Button(
              onClick = { showSubmitDialog = true },
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier
                .weight(1f)
                .testTag("submit_test_button"),
              colors = ButtonDefaults.buttonColors(containerColor = ApexEmerald)
            ) {
              Text("Submit Test")
            }
          }
        }
      }
    }
  }

  // Confirmation on Exit
  if (showExitDialog) {
    AlertDialog(
      onDismissRequest = { showExitDialog = false },
      title = { Text("Quit Test?") },
      text = { Text("Your current test answers will be discarded. Are you sure you want to exit?") },
      confirmButton = {
        Button(
          onClick = {
            showExitDialog = false
            onCancelTest()
          },
          colors = ButtonDefaults.buttonColors(containerColor = ApexCoralRed)
        ) {
          Text("Abandon Test")
        }
      },
      dismissButton = {
        TextButton(onClick = { showExitDialog = false }) {
          Text("Keep Practicing")
        }
      }
    )
  }

  // Confirmation on Submit
  if (showSubmitDialog) {
    val unansweredCount = totalQuestions - testState.userAnswers.size
    AlertDialog(
      onDismissRequest = { showSubmitDialog = false },
      title = { Text("Submit Diagnostic Test?") },
      text = {
        Column {
          Text("You have answered ${testState.userAnswers.size} of $totalQuestions questions.")
          if (unansweredCount > 0) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              "$unansweredCount questions remain unanswered. Unanswered questions will be scored as 0.",
              color = ApexCoralRed,
              fontWeight = FontWeight.Medium
            )
          }
          Spacer(modifier = Modifier.height(6.dp))
          Text("Submitting will generate your personalized study recommendations and award Mastery Sparks.")
        }
      },
      confirmButton = {
        Button(
          onClick = {
            showSubmitDialog = false
            onSubmitTest()
          },
          colors = ButtonDefaults.buttonColors(containerColor = ApexEmerald),
          modifier = Modifier.testTag("confirm_submit_test_button")
        ) {
          Text("Submit Now")
        }
      },
      dismissButton = {
        TextButton(onClick = { showSubmitDialog = false }) {
          Text("Review Answers")
        }
      }
    )
  }
}
