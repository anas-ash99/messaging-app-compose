package com.example.messagingapp

import androidx.compose.runtime.MutableState
import com.example.messagingapp.models.MessageModel

data class State(
    var isMicIcon:Boolean = true,
    var messageInput:String = "",
    var messages:MutableList<MessageModel> = mutableListOf(),
    var listState:Int = 0
)
