package com.example.messagingapp

import com.example.messagingapp.models.MessageModel

sealed class DataResources{
    object Loading:DataResources()
    data class Success(val messages:MutableList<MessageModel>):DataResources()
    data class Error(val error:Exception):DataResources()



}
