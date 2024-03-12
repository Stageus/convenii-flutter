package com.example.convenii

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import com.example.convenii.ui.theme.ConveniiTheme
import com.example.convenii.view.ConveniiApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConveniiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = Color.White
                ) {
                    ConveniiApp()

                }
            }
        }
    }
}


