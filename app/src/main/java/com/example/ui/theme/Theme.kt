package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

enum class AppThemeMode {
  DARK, LIGHT, AMOLED, HIGH_CONTRAST
}

private val DarkColorScheme = darkColorScheme(
  primary = ElectricBlue,
  onPrimary = Color.Black,
  primaryContainer = MidnightBlue,
  onPrimaryContainer = TextPrimaryDark,
  secondary = NeonCyan,
  onSecondary = Color.Black,
  secondaryContainer = CardBackgroundDark,
  onSecondaryContainer = TextPrimaryDark,
  tertiary = TechPurple,
  background = DeepNavy,
  onBackground = TextPrimaryDark,
  surface = DarkNavySurface,
  onSurface = TextPrimaryDark,
  surfaceVariant = CardBackgroundDark,
  onSurfaceVariant = TextSecondaryDark,
  outline = CardBorderDark,
  outlineVariant = TextTertiaryDark,
  error = ErrorCrimson
)

private val LightColorScheme = lightColorScheme(
  primary = LightPrimary,
  onPrimary = Color.White,
  primaryContainer = Color(0xFFE0F2FE),
  onPrimaryContainer = Color(0xFF0369A1),
  secondary = Color(0xFF0D9488),
  onSecondary = Color.White,
  secondaryContainer = Color(0xFFCCFBF1),
  onSecondaryContainer = Color(0xFF115E59),
  tertiary = Color(0xFF7C3AED),
  background = LightBackground,
  onBackground = LightTextPrimary,
  surface = LightSurface,
  onSurface = LightTextPrimary,
  surfaceVariant = Color(0xFFF1F5F9),
  onSurfaceVariant = LightTextSecondary,
  outline = LightCardBorder,
  error = ErrorCrimson
)

private val AmoledColorScheme = darkColorScheme(
  primary = NeonCyan,
  onPrimary = Color.Black,
  primaryContainer = Color(0xFF0A192F),
  onPrimaryContainer = Color.White,
  secondary = ElectricBlue,
  onSecondary = Color.Black,
  background = AmoledBackground,
  onBackground = Color.White,
  surface = AmoledSurface,
  onSurface = Color.White,
  surfaceVariant = Color(0xFF0F172A),
  onSurfaceVariant = Color(0xFFCBD5E1),
  outline = AmoledBorder,
  error = ErrorCrimson
)

private val HighContrastColorScheme = darkColorScheme(
  primary = HighContrastPrimary,
  onPrimary = Color.Black,
  secondary = HighContrastAccent,
  onSecondary = Color.Black,
  background = HighContrastBackground,
  onBackground = HighContrastText,
  surface = HighContrastSurface,
  onSurface = HighContrastText,
  outline = Color.White,
  error = Color(0xFFFF5555)
)

@Composable
fun LearnDataStructuresTheme(
  themeMode: AppThemeMode = AppThemeMode.DARK,
  content: @Composable () -> Unit
) {
  val colorScheme = when (themeMode) {
    AppThemeMode.DARK -> DarkColorScheme
    AppThemeMode.LIGHT -> LightColorScheme
    AppThemeMode.AMOLED -> AmoledColorScheme
    AppThemeMode.HIGH_CONTRAST -> HighContrastColorScheme
  }

  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as? Activity)?.window
      if (window != null) {
        window.statusBarColor = colorScheme.background.toArgb()
        window.navigationBarColor = colorScheme.surface.toArgb()
        WindowCompat.getInsetsController(window, view).apply {
          isAppearanceLightStatusBars = (themeMode == AppThemeMode.LIGHT)
          isAppearanceLightNavigationBars = (themeMode == AppThemeMode.LIGHT)
        }
      }
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = AppTypography,
    content = content
  )
}
