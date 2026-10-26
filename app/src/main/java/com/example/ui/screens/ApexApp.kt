package com.example.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Insights
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.ui.viewmodel.ApexViewModel
import com.example.ui.viewmodel.NavigationTab

@Composable
fun ApexApp(
  viewModel: ApexViewModel,
  modifier: Modifier = Modifier
) {
  val uiState by viewModel.uiState.collectAsState()
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(uiState.snackbarMessage) {
    uiState.snackbarMessage?.let { msg ->
      snackbarHostState.showSnackbar(msg)
      viewModel.clearSnackbar()
    }
  }

  // 1. First-time or logged-out flow: Show Login Screen
  if (!uiState.isLoggedIn) {
    LoginScreen(
      onLoginGoogle = viewModel::loginWithGoogle,
      onLoginFacebook = viewModel::loginWithFacebook,
      onLoginPhone = viewModel::loginWithPhone,
      modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.safeDrawing)
    )
  }
  // 2. Focused test view when a diagnostic test is active
  else if (uiState.activeTest.isActive) {
    ActiveTestScreen(
      testState = uiState.activeTest,
      onSelectAnswer = viewModel::selectAnswer,
      onToggleFlag = viewModel::toggleFlag,
      onNavigateQuestion = viewModel::navigateQuestion,
      onSubmitTest = viewModel::submitTest,
      onCancelTest = viewModel::cancelTest,
      modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.safeDrawing)
    )
  }
  // 3. Main scaffold with 5-tab navigation
  else {
    Scaffold(
      modifier = modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.safeDrawing),
      snackbarHost = { SnackbarHost(snackbarHostState) },
      bottomBar = {
        NavigationBar(
          modifier = Modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .testTag("apex_bottom_nav"),
          containerColor = MaterialTheme.colorScheme.surface,
          tonalElevation = androidx.compose.ui.unit.Dp(8f)
        ) {
          NavigationBarItem(
            selected = uiState.currentTab == NavigationTab.DASHBOARD,
            onClick = { viewModel.switchTab(NavigationTab.DASHBOARD) },
            icon = {
              Icon(
                if (uiState.currentTab == NavigationTab.DASHBOARD) Icons.Filled.Home else Icons.Outlined.Home,
                contentDescription = "Home"
              )
            },
            label = { Text("Home") },
            modifier = Modifier.testTag("nav_item_dashboard")
          )

          NavigationBarItem(
            selected = uiState.currentTab == NavigationTab.DIAGNOSTICS,
            onClick = { viewModel.switchTab(NavigationTab.DIAGNOSTICS) },
            icon = {
              Icon(
                if (uiState.currentTab == NavigationTab.DIAGNOSTICS) Icons.Filled.Insights else Icons.Outlined.Insights,
                contentDescription = "Diagnostics"
              )
            },
            label = { Text("Diagnostics") },
            modifier = Modifier.testTag("nav_item_diagnostics")
          )

          NavigationBarItem(
            selected = uiState.currentTab == NavigationTab.PRIZES,
            onClick = { viewModel.switchTab(NavigationTab.PRIZES) },
            icon = {
              Icon(
                if (uiState.currentTab == NavigationTab.PRIZES) Icons.Filled.AutoAwesome else Icons.Outlined.AutoAwesome,
                contentDescription = "Prizes"
              )
            },
            label = { Text("Prizes") },
            modifier = Modifier.testTag("nav_item_prizes")
          )

          NavigationBarItem(
            selected = uiState.currentTab == NavigationTab.LEADERBOARD,
            onClick = { viewModel.switchTab(NavigationTab.LEADERBOARD) },
            icon = {
              Icon(
                if (uiState.currentTab == NavigationTab.LEADERBOARD) Icons.Filled.EmojiEvents else Icons.Outlined.EmojiEvents,
                contentDescription = "Ranks"
              )
            },
            label = { Text("Leaderboard") },
            modifier = Modifier.testTag("nav_item_leaderboard")
          )

          NavigationBarItem(
            selected = uiState.currentTab == NavigationTab.ACCOUNT,
            onClick = { viewModel.switchTab(NavigationTab.ACCOUNT) },
            icon = {
              Icon(
                if (uiState.currentTab == NavigationTab.ACCOUNT) Icons.Filled.AccountCircle else Icons.Outlined.AccountCircle,
                contentDescription = "Account"
              )
            },
            label = { Text("Account") },
            modifier = Modifier.testTag("nav_item_account")
          )
        }
      }
    ) { innerPadding ->
      Box(modifier = Modifier.padding(innerPadding)) {
        Crossfade(targetState = uiState.currentTab, label = "TabCrossfade") { tab ->
          when (tab) {
            NavigationTab.DASHBOARD -> DashboardScreen(
              state = uiState,
              onStartTest = viewModel::startTest,
              onOpenPrizeVault = { viewModel.switchTab(NavigationTab.PRIZES) },
              onOpenDiagnostics = { viewModel.switchTab(NavigationTab.DIAGNOSTICS) },
              onOpenAccount = { viewModel.switchTab(NavigationTab.ACCOUNT) }
            )
            NavigationTab.DIAGNOSTICS -> DiagnosticsScreen(
              latestReport = uiState.latestReport,
              testHistory = uiState.testHistory,
              isAiLoading = uiState.isAiAdviceLoading,
              onNavigateToPrizes = { viewModel.switchTab(NavigationTab.PRIZES) },
              onStartNewTest = { viewModel.switchTab(NavigationTab.DASHBOARD) }
            )
            NavigationTab.PRIZES -> PrizesScreen(
              prizes = uiState.prizes,
              totalSparks = uiState.totalSparks,
              selectedPrizeForStudy = uiState.selectedPrizeForStudy,
              onUnlockPrize = viewModel::unlockPrize,
              onViewPrize = viewModel::viewPrize,
              onCloseViewer = viewModel::closePrizeViewer
            )
            NavigationTab.LEADERBOARD -> LeaderboardScreen(
              leaderboard = uiState.leaderboard,
              totalSparks = uiState.totalSparks
            )
            NavigationTab.ACCOUNT -> AccountSettingsScreen(
              state = uiState,
              onUpdateProfile = viewModel::updateProfile,
              onUpdatePreferences = viewModel::updatePreferences,
              onLogout = viewModel::logout,
              onSwitchAccount = viewModel::logout
            )
          }
        }
      }
    }
  }
}
