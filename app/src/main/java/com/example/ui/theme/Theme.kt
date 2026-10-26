package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
  primary = ApexPrimaryDarkTheme,
  onPrimary = Color(0xFF0F172A),
  primaryContainer = ApexPrimaryLight,
  onPrimaryContainer = Color.White,
  secondary = ApexGold,
  onSecondary = Color(0xFF451A03),
  secondaryContainer = ApexGoldDark,
  onSecondaryContainer = Color.White,
  tertiary = ApexEmerald,
  background = ApexBackgroundDark,
  surface = ApexSurfaceDark,
  surfaceVariant = ApexSurfaceVariantDark,
  onBackground = ApexTextPrimaryDark,
  onSurface = ApexTextPrimaryDark,
  onSurfaceVariant = ApexTextSecondaryDark
)

private val LightColorScheme = lightColorScheme(
  primary = ApexPrimaryLight,
  onPrimary = Color.White,
  primaryContainer = ApexPrimaryContainer,
  onPrimaryContainer = ApexOnPrimaryContainer,
  secondary = ApexGold,
  onSecondary = Color.White,
  secondaryContainer = ApexGoldContainer,
  onSecondaryContainer = ApexOnGoldContainer,
  tertiary = ApexEmeraldDark,
  background = ApexBackground,
  surface = ApexSurface,
  surfaceVariant = ApexSurfaceVariant,
  outline = ApexOutline,
  onBackground = ApexTextPrimary,
  onSurface = ApexTextPrimary,
  onSurfaceVariant = ApexTextSecondary
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Set to false by default for consistent brand identity
  content: @Composable () -> Unit,
) {
  val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      val context = LocalContext.current
      if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }
    darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
