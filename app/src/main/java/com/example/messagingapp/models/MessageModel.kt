package com.example.messagingapp.models

data class MessageModel(

    var id:String = "",
    var chatId:String = "",
    var userId:String = "",
    var content:String,
    var date:String,
    var toUser:String ="",
)
