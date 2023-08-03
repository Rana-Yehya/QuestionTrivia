package com.example.questiontrivia.screens

import androidx.compose.runtime.Composable
import com.example.questiontrivia.components.QuestionsAnalysis

@Composable
fun QuestionTriviaHome(viewModel : QuestionViewModel) {
    QuestionsAnalysis(viewModel)
}