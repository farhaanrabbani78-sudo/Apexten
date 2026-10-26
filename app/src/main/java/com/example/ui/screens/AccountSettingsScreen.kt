package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ApexCoralRed
import com.example.ui.theme.ApexEmerald
import com.example.ui.theme.ApexGold
import com.example.ui.theme.ApexPrimary
import com.example.ui.theme.ApexPrimaryLight
import com.example.ui.viewmodel.ApexUiState

@Composable
fun AccountSettingsScreen(
  state: ApexUiState,
  onUpdateProfile: (name: String, email: String, phone: String, school: String, board: String, goal: String) -> Unit,
  onUpdatePreferences: (reminder: Boolean, sound: Boolean) -> Unit,
  onLogout: () -> Unit,
  onSwitchAccount: () -> Unit,
  modifier: Modifier = Modifier
) {
  var isEditingName by remember { mutableStateOf(false) }
  var editedName by remember(state.studentName) { mutableStateOf(state.studentName) }
  var editedEmail by remember(state.studentEmail) { mutableStateOf(state.studentEmail) }
  var editedPhone by remember(state.studentPhone) { mutableStateOf(state.studentPhone) }
  var editedSchool by remember(state.schoolName) { mutableStateOf(state.schoolName) }
  var editedBoard by remember(state.boardName) { mutableStateOf(state.boardName) }
  var editedGoal by remember(state.targetGoal) { mutableStateOf(state.targetGoal) }

  var dailyReminder by remember(state.dailyReminder) { mutableStateOf(state.dailyReminder) }
  var soundHaptics by remember(state.soundHaptics) { mutableStateOf(state.soundHaptics) }

  var showLogoutConfirm by remember { mutableStateOf(false) }
  var showColorPicker by remember { mutableStateOf(false) }
  var selectedAvatarColor by remember { mutableStateOf(Color(0xFF2563EB)) }

  val avatarColors = listOf(
    Color(0xFF2563EB), // Blue
    Color(0xFF10B981), // Emerald
    Color(0xFF8B5CF6), // Purple
    Color(0xFFF59E0B), // Amber
    Color(0xFFEC4899), // Pink
    Color(0xFF0EA5E9)  // Sky
  )

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Profile Hero Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          // Avatar circle with initial & color picker trigger
          Box(
            modifier = Modifier
              .size(80.dp)
              .clip(CircleShape)
              .background(
                Brush.linearGradient(
                  listOf(selectedAvatarColor, selectedAvatarColor.copy(alpha = 0.8f))
                )
              )
              .clickable { showColorPicker = !showColorPicker },
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = state.studentName.take(1).uppercase().ifBlank { "S" },
              fontSize = 34.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color.White
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Student Name & Edit inline button
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Text(
              text = state.studentName,
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
            IconButton(
              onClick = { isEditingName = !isEditingName },
              modifier = Modifier
                .size(32.dp)
                .testTag("edit_name_icon_button")
            ) {
              Icon(
                Icons.Default.Edit,
                contentDescription = "Edit Name",
                tint = ApexPrimaryLight,
                modifier = Modifier.size(18.dp)
              )
            }
          }

          // Provider badge
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
            modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                Icons.Default.Security,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Signed in via ${state.authProvider} (${state.studentEmail})",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Medium
              )
            }
          }

          // Quick stats row
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
          ) {
            StatMiniBadge(icon = Icons.Default.ElectricBolt, tint = ApexGold, value = "${state.totalSparks}", label = "Sparks")
            StatMiniBadge(icon = Icons.Default.LocalFireDepartment, tint = ApexGold, value = "${state.streakDays}d", label = "Streak")
            StatMiniBadge(icon = Icons.Default.Schedule, tint = ApexPrimaryLight, value = "${state.testsCompleted}", label = "Drills")
          }
        }
      }
    }

    // Avatar Color Picker dropdown if opened
    if (showColorPicker) {
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Text(
              text = "Choose Avatar Theme Color",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              avatarColors.forEach { color ->
                Box(
                  modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(color)
                    .border(
                      width = if (selectedAvatarColor == color) 3.dp else 0.dp,
                      color = if (selectedAvatarColor == color) Color.White else Color.Transparent,
                      shape = CircleShape
                    )
                    .clickable {
                      selectedAvatarColor = color
                      showColorPicker = false
                    },
                  contentAlignment = Alignment.Center
                ) {
                  if (selectedAvatarColor == color) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                  }
                }
              }
            }
          }
        }
      }
    }

    // 2. Edit Profile Form
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Personal & Academic Profile",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "Class 10 CBSE",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = ApexPrimaryLight
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Student Name Input
          OutlinedTextField(
            value = editedName,
            onValueChange = { editedName = it },
            label = { Text("Student Full Name") },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("edit_student_name"),
            singleLine = true,
            leadingIcon = {
              Icon(Icons.Default.AccountCircle, contentDescription = null, tint = ApexPrimaryLight)
            }
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Email Input
          OutlinedTextField(
            value = editedEmail,
            onValueChange = { editedEmail = it },
            label = { Text("Contact Email") },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("edit_student_email"),
            singleLine = true
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Mobile Number Input
          OutlinedTextField(
            value = editedPhone,
            onValueChange = { editedPhone = it },
            label = { Text("Mobile Phone Number") },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("edit_student_phone"),
            singleLine = true,
            leadingIcon = {
              Icon(Icons.Default.Phone, contentDescription = null, tint = ApexPrimaryLight)
            }
          )

          Spacer(modifier = Modifier.height(10.dp))

          // School Name Input
          OutlinedTextField(
            value = editedSchool,
            onValueChange = { editedSchool = it },
            label = { Text("School / Coaching Name") },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("edit_student_school"),
            singleLine = true,
            leadingIcon = {
              Icon(Icons.Default.School, contentDescription = null, tint = ApexPrimaryLight)
            }
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Target Exam Goal
          OutlinedTextField(
            value = editedGoal,
            onValueChange = { editedGoal = it },
            label = { Text("Target Goal") },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("edit_student_goal"),
            singleLine = true
          )

          Spacer(modifier = Modifier.height(16.dp))

          // Save Profile Button
          Button(
            onClick = {
              onUpdateProfile(
                editedName,
                editedEmail,
                editedPhone,
                editedSchool,
                editedBoard,
                editedGoal
              )
              isEditingName = false
            },
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("save_profile_button"),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = ApexPrimaryLight,
              contentColor = Color.White
            )
          ) {
            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Save Profile Changes", fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // 3. Study & App Preferences
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
        ) {
          Text(
            text = "App & Notification Settings",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )

          Spacer(modifier = Modifier.height(12.dp))

          // Daily Study Reminder Toggle
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.weight(1f)
            ) {
              Icon(Icons.Default.Notifications, contentDescription = null, tint = ApexGold)
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = "Daily Diagnostic Reminder",
                  style = MaterialTheme.typography.bodyMedium,
                  fontWeight = FontWeight.SemiBold
                )
                Text(
                  text = "Prompt to complete 10-min test at 6:00 PM",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
            Switch(
              checked = dailyReminder,
              onCheckedChange = {
                dailyReminder = it
                onUpdatePreferences(dailyReminder, soundHaptics)
              },
              modifier = Modifier.testTag("toggle_daily_reminder")
            )
          }

          HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

          // Sound Effects Toggle
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.weight(1f)
            ) {
              Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, tint = ApexPrimaryLight)
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = "Test Sounds & Haptics",
                  style = MaterialTheme.typography.bodyMedium,
                  fontWeight = FontWeight.SemiBold
                )
                Text(
                  text = "Audio cues for timer countdown & submissions",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
            Switch(
              checked = soundHaptics,
              onCheckedChange = {
                soundHaptics = it
                onUpdatePreferences(dailyReminder, soundHaptics)
              },
              modifier = Modifier.testTag("toggle_sound_effects")
            )
          }
        }
      }
    }

    // 4. Account Management & Logout Actions
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
        ) {
          Text(
            text = "Account & Session",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )

          Spacer(modifier = Modifier.height(12.dp))

          // Switch Account Button
          OutlinedButton(
            onClick = onSwitchAccount,
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("switch_account_button"),
            shape = RoundedCornerShape(12.dp)
          ) {
            Icon(Icons.Default.SwapHoriz, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Switch or Re-login Account")
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Log Out Button
          Button(
            onClick = { showLogoutConfirm = true },
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("logout_button"),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = ApexCoralRed.copy(alpha = 0.12f),
              contentColor = ApexCoralRed
            )
          ) {
            Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Sign Out", fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }

  // --- LOGOUT CONFIRMATION DIALOG ---
  if (showLogoutConfirm) {
    AlertDialog(
      onDismissRequest = { showLogoutConfirm = false },
      title = { Text(text = "Sign Out?") },
      text = {
        Text("Are you sure you want to log out of ApexTen? You will be prompted to log in again on first launch.")
      },
      confirmButton = {
        Button(
          onClick = {
            showLogoutConfirm = false
            onLogout()
          },
          colors = ButtonDefaults.buttonColors(containerColor = ApexCoralRed),
          modifier = Modifier.testTag("confirm_logout_btn")
        ) {
          Text("Yes, Sign Out")
        }
      },
      dismissButton = {
        TextButton(onClick = { showLogoutConfirm = false }) {
          Text("Cancel")
        }
      }
    )
  }
}

@Composable
fun StatMiniBadge(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  tint: Color,
  value: String,
  label: String
) {
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(16.dp))
      Spacer(modifier = Modifier.width(6.dp))
      Column {
        Text(
          text = value,
          style = MaterialTheme.typography.labelLarge,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = label,
          style = MaterialTheme.typography.labelSmall,
          fontSize = 10.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}
