package com.example.messagingapp.models

data class ChatModel(var messages:MutableList<MessageModel> = mutableListOf()){
    //// empty constructor for firestore DocumentSnapshot casting
    constructor(): this(mutableListOf())
}
