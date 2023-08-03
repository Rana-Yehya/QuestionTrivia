package com.example.questiontrivia.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questiontrivia.repository.QuestionRepository
import com.example.questiontrivia.data.DataOrException
import com.example.questiontrivia.models.QuestionItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val questionRepository: QuestionRepository)
    : ViewModel(){
    // WHY NOT FLOW??
    val questions : MutableState<DataOrException<
            ArrayList<QuestionItem>,Boolean,Exception>>
    = mutableStateOf( DataOrException(null,true,Exception("")))

    init {
        getAllQuestions()
    }
    fun getAllQuestions(){
        viewModelScope.launch {
            questions.value.loading = true
            questions.value = questionRepository.getAllQuestions()
            if(questions.value.data.toString().isNotEmpty()){
                questions.value.loading = false
            }
        }

    }

}