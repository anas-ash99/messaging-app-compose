package com.example.messagingapp

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.example.messagingapp.models.ChatModel
import com.example.messagingapp.models.MessageModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


class Firestore {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("chats")

    fun sendMessage(state: State){

        collection.document(state.messages[state.messages.size -1].chatId).set(ChatModel(state.messages)).addOnSuccessListener {
        }.addOnFailureListener {
            println(it)
            Log.e("firebase", it.message,it)
        }
    }

    fun listenToNewMessages(state:MutableState<State>){

        try {


            collection.document(state.value.chatId).addSnapshotListener{ value, error ->

                if (error != null){
                    Log.e("firestore", error.message,error)

                }
                value?.let {
                    val chat:ChatModel? = value.toObject(ChatModel::class.java)

                    if (chat!= null){
                        state.value = state.value.copy(messages = chat.messages, isMessagesLoading = false, listState = chat.messages.size)
                    }else{
                        state.value = state.value.copy( isMessagesLoading = false)
                    }
                }
            }

       }catch (e:Exception){
           Log.e("firebase", e.message,e)
       }
    }

}


