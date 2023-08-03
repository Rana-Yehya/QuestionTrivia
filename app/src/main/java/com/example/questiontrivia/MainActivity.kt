package com.example.questiontrivia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.questiontrivia.screens.QuestionTriviaHome
import com.example.questiontrivia.screens.QuestionViewModel
import com.example.questiontrivia.ui.theme.QuestionTriviaTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuestionTriviaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val questionViewModel: QuestionViewModel by viewModels()
                    QuestionTriviaHome(questionViewModel)
                }
            }
        }
    }
}
