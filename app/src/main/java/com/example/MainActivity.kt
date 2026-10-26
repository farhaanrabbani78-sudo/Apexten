package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.ui.screens.ApexApp
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.ApexViewModel

class MainActivity : ComponentActivity() {
  private val viewModel: ApexViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        ApexApp(viewModel = viewModel)
      }
    }
  }
}
