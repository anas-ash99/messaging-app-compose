package com.example.messagingapp.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.messagingapp.Firestore
import com.example.messagingapp.State
import com.example.messagingapp.UserActions
import com.example.messagingapp.models.MessageModel
import java.time.LocalDateTime
import java.util.UUID

class MainViewModel: ViewModel() {


    var state = mutableStateOf(State())
    private val firestore = Firestore()


    private fun onSendClick(){
    val id = UUID.randomUUID().toString()
    val message =
        MessageModel(id,state.value.chatId, state.value.userId, state.value.messageInput, LocalDateTime.now().hour.toString() + ":"+ LocalDateTime.now().minute.toString(), state.value.otherUserId)
        state.value.messages.add(message)
        state.value = state.value.copy(messages = state.value.messages, messageInput = "", isMicIcon = true, listState = state.value.messages.size )
        firestore.sendMessage(state.value)

    }

    private fun takeInput(string:String){
        state.value = state.value.copy(isMicIcon = string.isBlank(), messageInput = string)
    }



    private fun listenToNewMessagesFireStore(){
        state.value = state.value.copy(isMessagesLoading = true)
        firestore.listenToNewMessages(state)

    }



    fun onAction(action:UserActions){
        when(action){
            UserActions.AttachmentIconClick -> {}
            is UserActions.MessageLongPress -> {}
            UserActions.MicClick -> {}
            is UserActions.SendMessage -> onSendClick()
            is UserActions.TypeMessage-> takeInput(action.input)
            is UserActions.OnDialogDoneClick -> {
                onDialogDoneClick(action.userId, action.otherUser, action.chatId)
            }
            UserActions.MenuIconClick -> state.value = state.value.copy(isDialogShown = true)
        }

    }

    private fun onDialogDoneClick(userId:String, otherUser:String, chatId: String) {
        state.value = State()
        state.value = state.value.copy(chatId = chatId, messages = mutableListOf(), isDialogShown = false, userId = userId, otherUserId = otherUser, listState = state.value.messages.size)
        listenToNewMessagesFireStore()
    }
}