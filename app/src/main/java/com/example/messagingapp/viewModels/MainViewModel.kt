package com.example.messagingapp.viewModels

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.messagingapp.State
import com.example.messagingapp.UserActions
import com.example.messagingapp.models.MessageModel
import java.time.LocalDateTime

class MainViewModel: ViewModel() {


    var state = mutableStateOf(State())

    val messageValue = mutableStateOf("")
    val loggedInUser = "123"
    val messages = mutableListOf(MessageModel(content = "Hi", date =  "12:50"), MessageModel(content = "Hi", date =  "12:50"), MessageModel(content = "Hi", date =  "12:50"), MessageModel(content = "Hi", date =  "12:50"), MessageModel(content = "Hi", date =  "12:50"), MessageModel(content = "Hi", date =  "12:50"), MessageModel(content = "Hi", date =  "12:50"), MessageModel(content = "Hi", date =  "12:50"), MessageModel("123",content = "hi there", date =  "12:50"), MessageModel("123",content = "how are you i haven't heard from you since to loong", date =  "13:50"),MessageModel("123",content = "how are you i haven't heard from you since to loong", date =  "13:50"),MessageModel("123",content = "how are you i haven't heard from you since to loong", date =  "13:50"),MessageModel("123",content = "how are you i haven't heard from you since to loong", date =  "13:50"),MessageModel("123",content = "how are you i haven't heard from you since to loong", date =  "13:50"),MessageModel(content = "Hi", date =  "12.50"))
    val listState = mutableStateOf(messages.size)
    fun onSendClick(){

        state.value.messages.add(MessageModel("123",state.value.messageInput, LocalDateTime.now().hour.toString() + ":"+ LocalDateTime.now().minute.toString()))
        state.value = state.value.copy(messages = state.value.messages, messageInput = "", isMicIcon = true, listState = state.value.messages.size )

    }

    fun takeInput(string:String){
        state.value = state.value.copy(isMicIcon = string.isBlank(), messageInput = string)
    }

    fun init() {
        state.value = state.value.copy(messages = messages, listState = messages.size)
    }


    fun onAction(action:UserActions){
        when(action){
            UserActions.AttachmentIconClick -> {}
            is UserActions.MessageLongPress -> {
                println(action.message)
            }
            UserActions.MicClick -> {}
            is UserActions.SendMessage -> onSendClick()
        }

    }
}