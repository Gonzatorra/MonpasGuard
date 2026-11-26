package com.example.monpasguardapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monpasguardapp.ui.SosScreen
import com.example.monpasguardapp.ui.theme.MonpasGuardAppTheme
import com.example.monpasguardapp.viewmodel.SosViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MonpasGuardAppTheme {
                val sosViewModel: SosViewModel = viewModel()

                SosScreen(
                    viewModel = sosViewModel
                )
            }
        }
    }
}