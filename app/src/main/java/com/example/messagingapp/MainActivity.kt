package com.example.messagingapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.messagingapp.models.MessageModel
import com.example.messagingapp.ui.theme.MessagingAppTheme
import com.example.messagingapp.viewModels.MainViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setContent {

            if (viewModel.state.value.isDialogShown){
                ChatIdDialog(state = viewModel.state){ userId, otherUserId, chatId ->
                    viewModel.onAction(UserActions.OnDialogDoneClick(userId, otherUserId, chatId))
                }
            }

            if (viewModel.state.value.isMessagesLoading){
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(Modifier.fillMaxSize(0.14f), color = Color.Blue )
                }
            }
            ChatScreen(state = viewModel.state, onAction = viewModel::onAction)

        }
    }



}



@Composable
fun ChatScreen(
   state: MutableState<State>,
   onAction:(UserActions)->Unit
){
    val listState:LazyListState  = rememberLazyListState(state.value.listState)
    val coroutineScope = rememberCoroutineScope()
    Column(verticalArrangement = Arrangement.SpaceBetween) {
        ContactHeader(state= state.value, painter = painterResource(id = R.drawable.ic_baseline_person_24), onMenuClick = {onAction(UserActions.MenuIconClick)})
        Divider()


        LazyColumn(
            modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Bottom,
            state = listState,
        ){
            items(state.value.messages){ message ->
                if (message.userId == state.value.userId){
                    MessageContainer(text = message.content, date = message.date, Alignment.End, Color.Green,
                        onMessageClick = {onAction(UserActions.MessageLongPress(message))})
                }else{
                    MessageContainer(text = message.content, date = message.date, Alignment.Start, Color.Cyan,
                        onMessageClick = {onAction(UserActions.MessageLongPress(message))})
                }

            }
            coroutineScope.launch {

                listState.animateScrollToItem(state.value.messages.size)
            }

        }
        Divider()
        BottomContainer(
            value = state.value.messageInput,
            isMicIcon = state.value.isMicIcon,
            onSendClick = {
                onAction(UserActions.SendMessage)
                coroutineScope.launch {
                    listState.animateScrollToItem(state.value.listState)
                }
            },
            onChangeValue = {onAction(UserActions.TypeMessage(it))}
        )



    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MessagingAppTheme {

    }
}