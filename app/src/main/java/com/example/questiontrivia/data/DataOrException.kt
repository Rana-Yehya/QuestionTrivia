package com.example.questiontrivia.data
// The reason of creating this class is to create some sort of communication between the
// network web site and the app
// to insure the data is transferred proparly
// Also, to make sure that the data can be anything in the app (not just QuestionItem)
data class DataOrException<T, Boolean, E:Exception>(
    var data: T? = null,
    var loading : Boolean? = null,
    var Except: E? = null
)
