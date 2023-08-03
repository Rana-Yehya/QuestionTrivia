package com.example.questiontrivia.repository

import android.util.Log
import com.example.questiontrivia.data.DataOrException
import com.example.questiontrivia.models.QuestionItem
import com.example.questiontrivia.network.QuestionAPI
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject


class QuestionRepository @Inject constructor(
    private val questionAPI: QuestionAPI
) {

    private val questions =
        DataOrException<ArrayList<QuestionItem>,
                Boolean,
                Exception>()


    suspend fun getAllQuestions() : DataOrException<ArrayList<QuestionItem>, Boolean, Exception>{
        try {
            questions.loading = true
            questions.data = questionAPI.getAllQuestions()
            if(questions.data.toString().isNotEmpty()) questions.loading = false
        }
        catch (e : Exception){
            questions.Except = e
            Log.d("Question Except", "Question Exception is ${questions.Except}")
        }
        return questions
    }
}