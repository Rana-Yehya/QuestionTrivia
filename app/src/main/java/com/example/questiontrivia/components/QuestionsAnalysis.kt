package com.example.questiontrivia.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.questiontrivia.models.QuestionItem
import com.example.questiontrivia.screens.QuestionViewModel
import com.example.questiontrivia.utils.AppColor

@Composable
fun QuestionsAnalysis(viewModel : QuestionViewModel){
    val questions = viewModel.questions.value.data?.toMutableList()

    val questionIndex = remember {
        mutableStateOf(0)
    }
    if(viewModel.questions.value.loading == true){
        Column(modifier = Modifier.padding(12.dp).fillMaxHeight(25f).fillMaxWidth(25f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
        Log.d("Loading", "Data loading....")
    }
    else {
        //viewmodel.questions.value.loading = false
        Log.d("Loading", "Data loaded")
        val question = try {
            questions?.get(questionIndex.value)
        }
        catch (e : java.lang.Exception){
            null
        }
        if(questions != null){
            QuestionDisplay(
                question!!,
                questionIndex,
                viewModel
            ){
                questionIndex.value ++

            }
        }
    }
}

@Composable
fun QuestionDisplay(
    question : QuestionItem,
    questionIndex : MutableState<Int>,
    viewmodel : QuestionViewModel,
    onNextClicked : (Int) -> Unit = {}
){
    val pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(10f,10f), phase = 0f)
    // By user
    val answer = remember(question){
        mutableStateOf<Int?>(null)
    }
    // By JSON
    val correctAnswer = remember(question){
        mutableStateOf<Boolean?>(null)
    }

    val choices = remember(question){
        question.choices.toMutableList()
    }

    // to check if the entered answer is correct
    val updateAnswer : (Int) -> Unit = remember(question){

        {//index
            answer.value = it;
            correctAnswer.value = choices[it] == question.answer
        }

    }

    Surface(
        modifier = Modifier
            .fillMaxSize(), color = AppColor.mDarkPurple
    ) {

        Column(modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {
            if(questionIndex.value > 3)ShowProgress(score = questionIndex.value)
            QuestionTracker(questionIndex.value,viewmodel.questions.value.data?.toMutableList()?.size )
            DrawDottedLine(pathEffect)
            Column(){
                Text(
                    text=question.question,
                    modifier = Modifier
                        // Will align make a difference?
                        .align(alignment = Alignment.Start)
                        .padding(15.dp)
                        .fillMaxHeight(0.45f),
                    color = AppColor.mOffWhite,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
                choices.forEachIndexed { index, choiceText ->
                    Row(modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color.Transparent)
                        .border(
                            width = 1.dp, brush = Brush.linearGradient(
                                colors = listOf(AppColor.mLightGray, AppColor.mLightBlue)
                            ),
                            shape = RoundedCornerShape(15.dp)
                            // .clip() here
                        ), verticalAlignment = Alignment.CenterVertically){
                        RadioButton(
                            selected =(answer.value == index) ,
                            onClick = { updateAnswer(index) },
                            modifier = Modifier.padding(start = 16.dp),
                            colors = RadioButtonDefaults
                                .colors(selectedColor =
                                if(correctAnswer.value == true && index == answer.value ){
                                    Color.Green.copy(alpha = 0.2f)
                                }else {
                                    Color.Red.copy(alpha = 0.2f)
                                }))
                        val annotatedString = buildAnnotatedString {
                            withStyle(style =  SpanStyle(fontWeight = FontWeight.W500,
                                color = if(correctAnswer.value == true && index == answer.value ){
                                    Color.Green
                                }else if(correctAnswer.value == false && index == answer.value ){
                                    Color.Red
                                }
                                else{
                                    AppColor.mOffWhite
                                }, fontSize =16.sp) ){
                                append(choiceText)
                            }

                        }
                        Text(text = annotatedString)
                    }
                }
                Button(
                    onClick = {onNextClicked(questionIndex.value)},
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = AppColor.mLightBlue

                    )
                ) {
                    Text(text = "Next",
                        modifier = Modifier.padding(5.dp),
                        color = AppColor.mOffWhite,
                        fontSize = 16.sp)

                }
            }
        }
    }
}

@Composable
fun ShowProgress(score: Int = 0){
    val gradient = Brush.linearGradient(listOf(AppColor.mLightBlue, AppColor.mLightPurple))
    val perentageState = remember(score){
        mutableStateOf(score*0.005f)
    }
    Row(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .height(50.dp)
        .border(
            width = 4.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    AppColor.mLightPurple,
                    AppColor.mLightPurple
                )
            ),
            shape = RoundedCornerShape(35.dp)
        )
        .clip(
            RoundedCornerShape(
                topStartPercent = 50,
                topEndPercent = 50,
                bottomStartPercent = 50,
                bottomEndPercent = 50
            )
        )
        .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically) {
        Button(
            contentPadding = PaddingValues(1.dp),
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(perentageState.value)
                .background(brush = gradient),
            enabled = false,
            elevation = null,
            colors = buttonColors(
                backgroundColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent
            )
        ){
            Text(text = (score * 10).toString(), modifier = Modifier
                .clip(shape = RoundedCornerShape(25.dp)).fillMaxWidth().fillMaxHeight(0.8f).padding(5.dp),
                color = AppColor.mOffWhite,
                textAlign = TextAlign.Center
                )
        }
    }
}

@Composable
fun DrawDottedLine(pathEffect: PathEffect){
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(2.dp)){
        drawLine(color = AppColor.mLightGray,
            start = Offset(0f,0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )
    }
}
@Composable
fun QuestionTracker(
    counter: Int = 0,
    QuestionsCount: Int? ,
    modifier: Modifier = Modifier){
    Text(buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
            withStyle(style = SpanStyle(color = AppColor.mLightGray,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp)
            ){
                append("Question ${counter}")
            }
            withStyle(style = SpanStyle(color = AppColor.mLightGray,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp)
            ){
                append("${QuestionsCount}")
            }
        }
    }, modifier = modifier.padding(10.dp))
}


