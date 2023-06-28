package com.example.messagingapp

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatIdDialog(
    state: MutableState<State>,
    onDoneClick:(userId:String, otherUserId:String, chatId:String)->Unit,

){
    val inputChatId = remember {
        mutableStateOf("")
    }
    val inputUserId = remember {
        mutableStateOf("")
    }
    val inputOtherUserId = remember {
        mutableStateOf("")
    }

    Dialog(onDismissRequest = {}, properties = DialogProperties(
        usePlatformDefaultWidth = false
    )) {
        Card(
            Modifier
                .fillMaxWidth(0.95f)
                .padding(10.dp)
                , shape = RoundedCornerShape(10.dp)) {
            Column(modifier = Modifier.padding(15.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                TextField(value = inputUserId.value, onValueChange = {inputUserId.value = it}, placeholder = { Text(
                    text = "Enter your id"
                )})

                TextField(value = inputChatId.value , onValueChange = {inputChatId.value = it}, placeholder = { Text(
                    text = "Enter chat id"
                )})
                Button(
                    onClick = {
                        if (inputUserId.value.isNotBlank()){
                            onDoneClick(inputUserId.value,inputOtherUserId.value, inputChatId.value )
                        } } ,
                    modifier = Modifier.fillMaxWidth(0.60f).height(60.dp).padding(top = 10.dp),
                    shape = RoundedCornerShape(15.dp)
                    ) {
                    Text(text = "Done")
                }
            }

        }

    }



}



