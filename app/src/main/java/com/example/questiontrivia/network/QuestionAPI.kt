package com.example.questiontrivia.network

import retrofit2.http.GET
import javax.inject.Singleton
import com.example.questiontrivia.models.Question as Question

@Singleton
interface QuestionAPI {

    @GET("world.json")
    suspend fun getAllQuestions(): Question
}