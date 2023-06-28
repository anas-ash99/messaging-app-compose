package com.example.messagingapp

import com.example.messagingapp.models.MessageModel


sealed class UserActions{
    object SendMessage:UserActions()
    data class MessageLongPress(val message: MessageModel):UserActions()
    data class TypeMessage(val input:String):UserActions()
    object AttachmentIconClick:UserActions()
    object MenuIconClick:UserActions()
    object MicClick:UserActions()
    data class OnDialogDoneClick(val userId:String, val otherUser:String, val chatId:String):UserActions()
}
