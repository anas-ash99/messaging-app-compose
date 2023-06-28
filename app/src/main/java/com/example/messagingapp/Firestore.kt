package com.example.messagingapp

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
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
    private val collection = db.collection("messages")

    fun sendMessage(message:MessageModel){
        collection.add(message).addOnSuccessListener {
        }.addOnFailureListener {
            println(it)
            Log.e("firebase", it.message,it)
        }
    }

    fun listenToNewMessages(state:MutableState<State>){

        try {

           collection.addSnapshotListener { value, error ->
               value?.let {
                   it.forEach { query->

                       if (!state.value.messages.any {it2 -> it2.id == query.get("id").toString() && state.value.chatId ==query.get("chatId").toString()  }){
                           val message = MessageModel(
                               id = query.get("id").toString(),
                               userId= query.get("userId").toString(),
                               toUser = query.get("toUser").toString(),
                               chatId = query.get("chatId").toString(),
                               content = query.get("content").toString(),
                               date = query.get("date").toString() )
                           state.value.messages.add(message)



                       }
                   }

                   state.value = state.value.copy(messages = state.value.messages, isMessagesLoading = false)
               }
           }
       }catch (e:Exception){
           Log.e("firebase", e.message,e)
       }
    }

}


