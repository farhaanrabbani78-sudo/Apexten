package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ApexEmerald
import com.example.ui.theme.ApexGold
import com.example.ui.theme.ApexPrimary
import com.example.ui.theme.ApexPrimaryLight

@Composable
fun LoginScreen(
  onLoginGoogle: (name: String, email: String) -> Unit,
  onLoginFacebook: (name: String, email: String) -> Unit,
  onLoginPhone: (phone: String, name: String) -> Unit,
  modifier: Modifier = Modifier
) {
  var showGoogleDialog by remember { mutableStateOf(false) }
  var showFacebookDialog by remember { mutableStateOf(false) }
  var showPhoneDialog by remember { mutableStateOf(false) }

  val scrollState = rememberScrollState()

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        Brush.verticalGradient(
          colors = listOf(
            ApexPrimary,
            Color(0xFF0F172A),
            Color(0xFF020617)
          )
        )
      )
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(horizontal = 24.dp, vertical = 32.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      // Top Educational Badge
      Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color.White.copy(alpha = 0.12f),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
        modifier = Modifier.padding(bottom = 16.dp)
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            Icons.Default.School,
            contentDescription = null,
            tint = ApexGold,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "CBSE CLASS 10 BOARD MASTERY",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            letterSpacing = 1.sp
          )
        }
      }

      // App Icon Logo
      Box(
        modifier = Modifier
          .size(76.dp)
          .clip(CircleShape)
          .background(
            Brush.linearGradient(
              listOf(Color(0xFF3B82F6), Color(0xFF1D4ED8))
            )
          )
          .border(2.dp, Color.White.copy(alpha = 0.3f), CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          Icons.Default.ElectricBolt,
          contentDescription = "ApexTen Logo",
          tint = ApexGold,
          modifier = Modifier.size(42.dp)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "ApexTen",
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.ExtraBold,
        color = Color.White
      )

      Text(
        text = "Class 10 Diagnostic Mastery & AI Guidance",
        style = MaterialTheme.typography.bodyMedium,
        color = Color(0xFFBFDBFE),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
      )

      Spacer(modifier = Modifier.height(28.dp))

      // Main Card containing sign in buttons
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "Get Started",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "Sign in to save your diagnostic scores, unlock study rewards, and track board progress",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
          )

          // 1. Google Sign-In Button
          Button(
            onClick = { showGoogleDialog = true },
            modifier = Modifier
              .fillMaxWidth()
              .height(52.dp)
              .testTag("login_button_google"),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = Color.White,
              contentColor = Color(0xFF1F2937)
            ),
            border = BorderStroke(1.5.dp, Color(0xFFE5E7EB)),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 1.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              GoogleIconGraphic(modifier = Modifier.size(22.dp))
              Spacer(modifier = Modifier.width(12.dp))
              Text(
                text = "Continue with Google",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // 2. Facebook Sign-In Button
          Button(
            onClick = { showFacebookDialog = true },
            modifier = Modifier
              .fillMaxWidth()
              .height(52.dp)
              .testTag("login_button_facebook"),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = Color(0xFF1877F2),
              contentColor = Color.White
            )
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              FacebookIconGraphic(modifier = Modifier.size(22.dp))
              Spacer(modifier = Modifier.width(12.dp))
              Text(
                text = "Continue with Facebook",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // 3. Mobile Number Sign-In Button
          OutlinedButton(
            onClick = { showPhoneDialog = true },
            modifier = Modifier
              .fillMaxWidth()
              .height(52.dp)
              .testTag("login_button_mobile"),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.5.dp, ApexPrimaryLight),
            colors = ButtonDefaults.outlinedButtonColors(
              contentColor = ApexPrimaryLight
            )
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Icon(
                Icons.Default.Phone,
                contentDescription = null,
                tint = ApexPrimaryLight,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = "Continue with Mobile Number",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
              )
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // Divider with or
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text(
              text = "  OR  ",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            HorizontalDivider(modifier = Modifier.weight(1f))
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Quick Guest option
          TextButton(
            onClick = { onLoginGoogle("Aryan Sharma", "aryan.guest@cbse10.edu") },
            modifier = Modifier.testTag("login_button_guest")
          ) {
            Text(
              text = "Explore as Guest Student (Demo Mode)",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // Trust & Security Notice
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = 16.dp)
      ) {
        Icon(
          Icons.Default.VerifiedUser,
          contentDescription = null,
          tint = ApexEmerald,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Safe & Private • Aligned with CBSE Class 10 Syllabus",
          style = MaterialTheme.typography.labelSmall,
          color = Color(0xFF94A3B8)
        )
      }
    }
  }

  // --- GOOGLE SIGN-IN DIALOG ---
  if (showGoogleDialog) {
    GoogleAccountDialog(
      onDismiss = { showGoogleDialog = false },
      onSelectAccount = { name, email ->
        showGoogleDialog = false
        onLoginGoogle(name, email)
      }
    )
  }

  // --- FACEBOOK SIGN-IN DIALOG ---
  if (showFacebookDialog) {
    FacebookAccountDialog(
      onDismiss = { showFacebookDialog = false },
      onConfirm = { name, email ->
        showFacebookDialog = false
        onLoginFacebook(name, email)
      }
    )
  }

  // --- MOBILE OTP SIGN-IN DIALOG ---
  if (showPhoneDialog) {
    MobileOtpDialog(
      onDismiss = { showPhoneDialog = false },
      onVerified = { phone, name ->
        showPhoneDialog = false
        onLoginPhone(phone, name)
      }
    )
  }
}

@Composable
fun GoogleAccountDialog(
  onDismiss: () -> Unit,
  onSelectAccount: (name: String, email: String) -> Unit
) {
  var customName by remember { mutableStateOf("") }
  var customEmail by remember { mutableStateOf("") }
  var isAddingCustom by remember { mutableStateOf(false) }

  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(20.dp),
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        GoogleIconGraphic(modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "Sign in with Google", fontWeight = FontWeight.Bold)
      }
    },
    text = {
      Column(modifier = Modifier.fillMaxWidth()) {
        Text(
          text = "Choose an account to continue to ApexTen Class 10 Prep",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (!isAddingCustom) {
          // Account 1: User's profile
          AccountItemCard(
            name = "Farhaan Rabbani",
            email = "farhaanrabbani78@gmail.com",
            initial = "F",
            avatarColor = Color(0xFF10B981),
            onClick = { onSelectAccount("Farhaan Rabbani", "farhaanrabbani78@gmail.com") },
            testTag = "google_account_primary"
          )

          Spacer(modifier = Modifier.height(8.dp))

          // Account 2: Sample student
          AccountItemCard(
            name = "Aryan Sharma",
            email = "aryan.s10@gmail.com",
            initial = "A",
            avatarColor = Color(0xFF2563EB),
            onClick = { onSelectAccount("Aryan Sharma", "aryan.s10@gmail.com") },
            testTag = "google_account_secondary"
          )

          Spacer(modifier = Modifier.height(12.dp))

          OutlinedButton(
            onClick = { isAddingCustom = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
          ) {
            Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Use another Google account")
          }
        } else {
          OutlinedTextField(
            value = customName,
            onValueChange = { customName = it },
            label = { Text("Student Full Name") },
            placeholder = { Text("e.g. Diya Sharma") },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("google_custom_name"),
            singleLine = true
          )
          Spacer(modifier = Modifier.height(8.dp))
          OutlinedTextField(
            value = customEmail,
            onValueChange = { customEmail = it },
            label = { Text("Gmail Address") },
            placeholder = { Text("student@gmail.com") },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("google_custom_email"),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
          )
        }
      }
    },
    confirmButton = {
      if (isAddingCustom) {
        Button(
          onClick = {
            val name = customName.ifBlank { "Google Student" }
            val email = customEmail.ifBlank { "student@gmail.com" }
            onSelectAccount(name, email)
          },
          modifier = Modifier.testTag("google_confirm_custom_btn")
        ) {
          Text("Continue")
        }
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel")
      }
    }
  )
}

@Composable
fun FacebookAccountDialog(
  onDismiss: () -> Unit,
  onConfirm: (name: String, email: String) -> Unit
) {
  var studentName by remember { mutableStateOf("Farhaan Rabbani") }

  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(20.dp),
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        FacebookIconGraphic(modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "Log In with Facebook", fontWeight = FontWeight.Bold)
      }
    },
    text = {
      Column(modifier = Modifier.fillMaxWidth()) {
        Text(
          text = "ApexTen will receive your name and profile picture to personalize your Class 10 tests.",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
          value = studentName,
          onValueChange = { studentName = it },
          label = { Text("Full Name") },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("fb_name_input"),
          singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        Surface(
          shape = RoundedCornerShape(12.dp),
          color = Color(0xFFEFF6FF),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Lock, contentDescription = null, tint = ApexPrimaryLight, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "We will never post anything to Facebook on your behalf.",
              style = MaterialTheme.typography.labelSmall,
              color = Color(0xFF1E3A8A)
            )
          }
        }
      }
    },
    confirmButton = {
      Button(
        onClick = {
          val name = studentName.ifBlank { "Farhaan Rabbani" }
          onConfirm(name, "farhaan.fb@facebook.com")
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1877F2)),
        modifier = Modifier.testTag("fb_confirm_btn")
      ) {
        Text("Continue as $studentName")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel")
      }
    }
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MobileOtpDialog(
  onDismiss: () -> Unit,
  onVerified: (phone: String, name: String) -> Unit
) {
  var countryCode by remember { mutableStateOf("+91") }
  var expandedCountry by remember { mutableStateOf(false) }
  var phoneNumber by remember { mutableStateOf("9876543210") }
  var studentName by remember { mutableStateOf("Farhaan Rabbani") }
  var otpCode by remember { mutableStateOf("") }
  var otpSent by remember { mutableStateOf(false) }
  var errorMessage by remember { mutableStateOf<String?>(null) }

  val countries = listOf("+91 (India)", "+1 (US/Canada)", "+44 (UK)", "+971 (UAE)")

  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(20.dp),
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Phone, contentDescription = null, tint = ApexPrimaryLight)
        Spacer(modifier = Modifier.width(10.dp))
        Text(
          text = if (!otpSent) "Login with Mobile Number" else "Verify OTP Code",
          fontWeight = FontWeight.Bold
        )
      }
    },
    text = {
      Column(modifier = Modifier.fillMaxWidth()) {
        if (!otpSent) {
          Text(
            text = "Enter your mobile phone number. We will send you an OTP for instant sign-in.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(16.dp))

          // Country Code Dropdown
          ExposedDropdownMenuBox(
            expanded = expandedCountry,
            onExpandedChange = { expandedCountry = !expandedCountry }
          ) {
            OutlinedTextField(
              value = countryCode,
              onValueChange = {},
              readOnly = true,
              label = { Text("Country") },
              trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCountry) },
              modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
            )
            ExposedDropdownMenu(
              expanded = expandedCountry,
              onDismissRequest = { expandedCountry = false }
            ) {
              countries.forEach { item ->
                DropdownMenuItem(
                  text = { Text(item) },
                  onClick = {
                    countryCode = item.substringBefore(" ")
                    expandedCountry = false
                  }
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Phone Number
          OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
              if (it.length <= 10 && it.all { ch -> ch.isDigit() }) phoneNumber = it
            },
            label = { Text("Mobile Number (10 digits)") },
            placeholder = { Text("9876543210") },
            leadingIcon = { Text(countryCode, modifier = Modifier.padding(start = 12.dp), fontWeight = FontWeight.Bold) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("mobile_number_input"),
            singleLine = true
          )

          Spacer(modifier = Modifier.height(12.dp))

          // Student Name
          OutlinedTextField(
            value = studentName,
            onValueChange = { studentName = it },
            label = { Text("Student Name") },
            placeholder = { Text("Farhaan Rabbani") },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("mobile_student_name"),
            singleLine = true
          )
        } else {
          Text(
            text = "Enter the 6-digit OTP code sent to $countryCode $phoneNumber",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(16.dp))

          OutlinedTextField(
            value = otpCode,
            onValueChange = {
              if (it.length <= 6 && it.all { ch -> ch.isDigit() }) otpCode = it
            },
            label = { Text("6-Digit OTP") },
            placeholder = { Text("782410") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("otp_code_input"),
            singleLine = true
          )

          Spacer(modifier = Modifier.height(8.dp))

          // Fast Auto-fill button for testing
          TextButton(
            onClick = { otpCode = "782410" },
            modifier = Modifier.testTag("autofill_otp_btn")
          ) {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = ApexGold, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Auto-fill Demo OTP (782410)", style = MaterialTheme.typography.labelSmall)
          }
        }

        errorMessage?.let { err ->
          Spacer(modifier = Modifier.height(8.dp))
          Text(text = err, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
      }
    },
    confirmButton = {
      if (!otpSent) {
        Button(
          onClick = {
            if (phoneNumber.length < 7) {
              errorMessage = "Please enter a valid phone number"
            } else {
              errorMessage = null
              otpSent = true
            }
          },
          modifier = Modifier.testTag("send_otp_btn")
        ) {
          Text("Send OTP")
        }
      } else {
        Button(
          onClick = {
            if (otpCode.length < 4) {
              errorMessage = "Please enter the 6-digit code (e.g. 782410)"
            } else {
              val fullPhone = "$countryCode $phoneNumber"
              onVerified(fullPhone, studentName.ifBlank { "Farhaan Rabbani" })
            }
          },
          modifier = Modifier.testTag("verify_otp_btn")
        ) {
          Text("Verify & Sign In")
        }
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel")
      }
    }
  )
}

@Composable
fun AccountItemCard(
  name: String,
  email: String,
  initial: String,
  avatarColor: Color,
  onClick: () -> Unit,
  testTag: String
) {
  Card(
    onClick = onClick,
    modifier = Modifier
      .fillMaxWidth()
      .testTag(testTag),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    )
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(38.dp)
          .clip(CircleShape)
          .background(avatarColor),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = initial,
          color = Color.White,
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp
        )
      }
      Spacer(modifier = Modifier.width(12.dp))
      Column {
        Text(
          text = name,
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = email,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}

@Composable
fun GoogleIconGraphic(modifier: Modifier = Modifier) {
  // Simple multi-colored Google 'G' graphic
  Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = "G",
      fontSize = 18.sp,
      fontWeight = FontWeight.ExtraBold,
      color = Color(0xFF4285F4)
    )
  }
}

@Composable
fun FacebookIconGraphic(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .clip(CircleShape)
      .background(Color.White),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = "f",
      fontSize = 18.sp,
      fontWeight = FontWeight.ExtraBold,
      color = Color(0xFF1877F2)
    )
  }
}
