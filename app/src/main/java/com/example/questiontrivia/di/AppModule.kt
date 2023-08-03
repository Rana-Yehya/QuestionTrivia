package com.example.questiontrivia.di

import com.example.questiontrivia.repository.QuestionRepository
import com.example.questiontrivia.network.QuestionAPI
import com.example.questiontrivia.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideQuestionRepository(questionAPI: QuestionAPI) = QuestionRepository(questionAPI = questionAPI)

    @Singleton
    @Provides
    fun provideQuestionAPI() : QuestionAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionAPI::class.java)

    }
}