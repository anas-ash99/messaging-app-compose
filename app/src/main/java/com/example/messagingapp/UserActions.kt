package com.example.messagingapp

import com.example.messagingapp.models.MessageModel

sealed class UserActions{
    object SendMessage:UserActions()
    data class MessageLongPress(val message: MessageModel):UserActions()
    object AttachmentIconClick:UserActions()
    object MicClick:UserActions()
}
