package com.example.messagingapp

import android.content.Intent
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.messagingapp.ui.theme.MessagingAppTheme
import com.example.messagingapp.viewModels.MainViewModel
import kotlinx.coroutines.launch
import com.example.messagingapp.permissions.Permissions.checkForAudioPermission

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setContent {
            viewModel.init()
            val listState:LazyListState  = rememberLazyListState(viewModel.state.value.messages.size)
            val coroutineScope = rememberCoroutineScope()

            Column(verticalArrangement = Arrangement.SpaceBetween) {
                ContactHeader(name = "Anas Ashraf", painter = painterResource(id = R.drawable.ic_baseline_person_24))
                Divider()
                LazyColumn(
                    modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Bottom,
                    state = listState
                ){
                     items(viewModel.state.value.messages){ message ->
                         if (message.userId ==viewModel.loggedInUser){
                             MessageContainer(text = message.content, date = message.date, Alignment.End, Color.Green,
                                 onMessageClick = {viewModel.onAction(UserActions.MessageLongPress(message))})
                         }else{
                             MessageContainer(text = message.content, date = message.date, Alignment.Start, Color.Cyan,
                                 onMessageClick = {viewModel.onAction(UserActions.MessageLongPress(message))})
                         }

                     }

                }
                Divider()
                BottomContainer(
                    value = viewModel.state.value.messageInput,
                    isMicIcon = viewModel.state.value.isMicIcon,
                    onSendClick = {
                        viewModel.onAction(UserActions.SendMessage)
                        coroutineScope.launch {
                            listState.animateScrollToItem(viewModel.state.value.listState)
                        }
                    },
                    onChangeValue = viewModel::takeInput
                )



            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

}





@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MessagingAppTheme {

    }
}