package com.example.mula

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mula.navigation.MulaNavGraph
import com.example.mula.ui.theme.MulaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MulaTheme {
                MulaNavGraph()
            }
        }
    }
}
